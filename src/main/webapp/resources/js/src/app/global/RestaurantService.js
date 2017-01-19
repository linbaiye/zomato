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
			addr["district"]  = token[token.length - 3];
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

	function shortCuisineList(data) {
		var cusinesToShow = [];
		/* The cuisineList is too long to display, show part of it and display it fully on demand. */
		for (var i = 0; i < 10 && i < data.cuisineList.length; i++) {
			cusinesToShow[i] = data.cuisineList[i];
		}
		return search = {
			first10Cuisines : cusinesToShow,
			categoryList : data.categoryList,
			cuisineList : data.cuisineList
		}
	}
	
	this.loadSearchCompoments = function() {
		var deferred = $q.defer();
		return $http.get(baseUrl + "/api/v1/search/components")
		.then(function (response) {
	     	 if (response.data.error != "EOK") {
        		 deferred.reject(response.data);
        	 } else {
        		 console.log(response.data)
        		 deferred.resolve(shortCuisineList(response.data.data));
        	 }
             return deferred.promise;
         }, function (response) {
        	deferred.reject(response.data);
            return deferred.promise;
         });
	}


	this.getRestuarantsByCategory = function(categoryId, page) {
		var deferred = $q.defer();
		return $http.get(baseUrl + "/api/v1/search/category/" + categoryId + "/" + page)
         .then(function (response) {
        	 if (response.data.error != "EOK") {
        		 deferred.reject(response.data);
        	 } else {
        		 console.log(response.data)
        		 deferred.resolve(transformRestaurantDetails(response.data.data));
        	 }
             return deferred.promise;
         }, function (response) {
        	deferred.reject(response.data);
             return deferred.promise;
         });
	}
	
}