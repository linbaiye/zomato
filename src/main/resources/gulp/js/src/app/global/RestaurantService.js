function RestaurantService($http, $q, baseUrl) {
	HttpPromiseSerivce.call(this, $http, $q);
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
			first10Places: placeListToShow,
			placeList: data.placeList
		}
	}


	this.loadSearchCompoments = function() {
		return this.commitPromise(baseUrl + "/api/v1/search/components", shortList);
	}

	this.getRecommandRestaurants = function() {
		return this.commitPromise(baseUrl + "/api/v1/search/recommend");
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

	this.loadRestaurantById = function(id) {
		return this.commitPromiseV2(baseUrl + "/api/v1/restaurant/" + id);
	}

	this.search = function(searchCriteria, currentPage) {
		return this.commitPromiseV2(baseUrl + "/api/v1/search/compound/restaurant", searchCriteria, function(data) {
			var list = [];
			for (var i = 0; i < data.hits.hits.length; i++) {
				var hit = data.hits.hits[i];
				var source = hit['_source'];
				var tmp = {
					id : hit['_id'],
					address : source['address']['text_address'],
					costForTwo: source['cost_for_2'],
					name: source['name'],
					thumbImageUrl: source['thumb_img_url'],
					rate: source['avg_rate']
				}
				tmp['cuisineSet'] = [];
				for (var j = 0 ; j < source['cuisines'].length; j++) {
					tmp['cuisineSet'].push({"name": source['cuisines'][j]});
				}
				tmp['featureList'] = []
				for (var j = 0; j < source['features'].length; j++) {
					tmp['featureList'].push({"name": source['features'][j]});
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
		});
	}
}
