function RestaurantService($http, $q, baseUrl) {

	function tConvert (time) {
		// Check correct time format and split into components
		time = time.toString ().match (/^([01]\d|2[0-3])(:)([0-5]\d):[0-5]\d?$/) || [time];
		if (time.length > 1) { // If time format correct
			time = time.slice (1);  // Remove full string match value
			time[4] = +time[0] < 12 ? ' AM' : ' PM'; // Set AM/PM
			time[0] = +time[0] % 12 || 12; // Adjust hours
		}
		return time.join (''); // return adjusted time or original string
	}

	function getTodayOpeningHours(hourList) {
		var dayMap = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
		var today = new Date().getDay();
		for (var j = 0; j < hourList.length; j++) {
			if (hourList[j].day == dayMap[today]) {
				if (hourList[j].start == hourList[j].end) {
					return "24 Hours";
				}
				return tConvert(hourList[j].start) + " to " + tConvert(hourList[j].end);
			}
		}
		return "Closed";
	}

	function transformRestaurantDetails(data) {
		for (var i = 0; i < data.restaurantList.length; i++) {
			var restaurant = data.restaurantList[i];
			var addr = restaurant.address;
			var token = addr.textAddress.split(",");
			token.splice(token.length - 1, 1);
			addr["address"] = token.join(", ");
			var cost = /.+A\$([.\d]+).*/g.exec(restaurant.approximatePrice);
			restaurant.costForTwo =  (!cost || cost.length < 2) ? "" : cost[1];
			restaurant.todayHours = getTodayOpeningHours(restaurant.openingHours);
		}
		return {
			restaurantList : data.restaurantList,
			currentPage : data.currentPage + 1,
			total: data.total
		}
	}

	function _commitRequest(deferred, url, callback, data, onRequestOkCb) {
		function onRequestError(response) {
			deferred.reject(response.data);
			return deferred.promise;
		}
		if (!data) {
			return $http.get(url).then(onRequestOkCb, onRequestError);
		}
		return $http.post(url, JSON.stringify(data), {headers: {'Content-Type': 'Application/json'}})
			.then(onRequestOkCb, onRequestError);
	}

	function commitPromiseV2(url, callback, data) {
		var deferred = $q.defer();
		function onRequestOk(response) {
			if (callback) {
					deferred.resolve(callback(response.data));
			} else {
					deferred.resolve(response.data);
			}
			return deferred.promise;
		}
		return _commitRequest(deferred, url, callback, data, onRequestOk);
	}

	function commitPromise(url, callback, data) {
		var deferred = $q.defer();
		function onRequestOk(response) {
			if (response.data.error != "EOK") {
				deferred.reject(response.data);
			} else {
				if (callback) {
					deferred.resolve(callback(response.data.data));
				} else {
					deferred.resolve(response.data.data);
				}
			}
			return deferred.promise;
		}
		return _commitRequest(deferred, url, callback, data, onRequestOk);
	}

	function shortList(data) {
		var cusinesToShow = [];
		/* The cuisineList is too long to display, show part of it and display it fully on demand. */
		for (var i = 0; i < 10 && i < data.cuisineList.length; i++) {
			cusinesToShow[i] = data.cuisineList[i];
		}
		var placeListToShow = [];
		for (var i = 0; i < 10 && i < data.placeList.length; i++) {
			placeListToShow[i] = data.placeList[i];
		}
		return search = {
			first10Cuisines : cusinesToShow,
			categoryList : data.categoryList,
			cuisineList : data.cuisineList,
			placeList : placeListToShow
		}
	}


	this.loadSearchCompoments = function() {
		return commitPromise(baseUrl + "/api/v1/search/components", shortList);
	}


	this.getRestuarantsByCategory = function(categoryId, page) {
		return commitPromise(baseUrl + "/api/v1/search/category/" + categoryId + "/" + page, transformRestaurantDetails);
	}

	this.getRecommandRestaurants = function() {
		return commitPromise(baseUrl + "/api/v1/search/recommand");
	}

	/* Copyied from stackoverflow. */
	function toTimeString(sec_num) {
	 	var hours   = Math.floor(sec_num / 3600);
	 	var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
	 	var seconds = sec_num - (hours * 3600) - (minutes * 60);
	 	if (hours   < 10) {hours   = "0"+hours;}
	 	if (minutes < 10) {minutes = "0"+minutes;}
	 	if (seconds < 10) {seconds = "0"+seconds;}
	 	return hours+':'+minutes+':'+seconds;
	}


	this.search = function(searchCriteria, currentPage) {
		return commitPromiseV2(baseUrl + "/api/v1/search/compound", function(data) {
			var list = [];
			for (var i = 0; i < data.hits.hits.length; i++) {
				var hit = data.hits.hits[i];
				var source = hit['_source'];
				var tmp = {
					id : hit['_id'],
					address : source['address']['text_address'],
					costForTwo: source['cost_for_2'],
					featureList: source['features'],
					name: source['name'],
					thumbImageUrl: source['thumb_img_url'],
					rate: source['avg_rate']
				}
				tmp['cuisineSet'] = [];
				for (var j = 0 ; j < source['cuisines'].length; j++) {
					tmp['cuisineSet'].push({"name": source['cuisines'][j]});
				}
				var open_hours = hit['_source']['open_hours'];
				var open_hour_list = [];
				for (var day in open_hours) {
					if (open_hours[day]['start_second'] == -1) {
						continue;
					}
					open_hour_list.push({'day': day, 'start': toTimeString(open_hours[day]['start_second']), 'end': toTimeString(open_hours[day]['end_second'])});
				}
				tmp['todayHours'] = getTodayOpeningHours(open_hour_list);
				list.push(tmp);
			}
			return {
				total: data.hits.total,
				restaurantList: list,
				currentPage: currentPage
			};
		}, searchCriteria);
	}
}
