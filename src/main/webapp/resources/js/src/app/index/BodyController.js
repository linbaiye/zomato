/**
 * 
 */

function BodyController($http, $location, baseUrl, broker) {
	var vm = this;
	

	
	function initFeaturedCollections(response) {
		var data = response.data;
		if (data.error !== "EOK") {
			return;
		}
		vm.collections = data.data;
		vm.imgUrl = 'https://b.zmtcdn.com/data/collections/6190ad66dfd037c7c4070fc428bfdad5_1481519457.jpg?fit=around%7C300%3A250&crop=300%3A250%3B%2A%2C%2A';
	}

	function initQuickSearch(response) {
		var data = response.data;
		if (data.error !== "EOK") {
			return;
		}
		vm.quickSearches = data.data;
	}

	function initStatistic(response) {
		var data = response.data;
		if (data.error != "EOK") {
			return;
		}
		vm.stats = data.data;
	}

	vm.gotoCollection = function(featured) {
		$location.path("feature/" + featured);
	}
	vm.gotoCategorySearch = function(categoryId) {
		$location.path("search/category/" + categoryId);
	}
	$http.get(baseUrl + "/api/v1/feature/main_page").then(initFeaturedCollections, function(response) {	});
	$http.get(baseUrl + "/api/v1/category").then(initQuickSearch, function(response) {	});
	$http.get(baseUrl + "/api/v1/restaurant/stats").then(initStatistic, function(response){ console.log(response); });
}