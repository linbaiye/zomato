/**
 * 
 */

function BodyController($http, $location, baseUrl, broker) {
	var vm = this;
	
	function shortenDescription(description) {
		if (description.length > 65) {
			return description.substr(0, 62) + "...";
		}
		return description;
	}
	
	function initFeaturedCollections(response) {
		var data = response.data;
		if (data.error !== "EOK") {
			return;
		}
		var collectionMap = {};
		for (var i = 0; i < data.data.length; i++) {
			data.data[i]['shortDesc'] = shortenDescription(data.data[i]['description']);
			collectionMap[data.data[i]["type"]] = data.data[i];
		}
		vm.collections = data.data;
		vm.imgUrl = 'https://b.zmtcdn.com/data/collections/6190ad66dfd037c7c4070fc428bfdad5_1481519457.jpg?fit=around%7C300%3A250&crop=300%3A250%3B%2A%2C%2A';
	}

	function initQuickSearch() {
		vm.quickSearches = [
			{imgUrl: "https://b.zmtcdn.com/images/search_tokens/app_icons/category_8.png", type:"Breakfast"},
			{imgUrl: "https://b.zmtcdn.com/images/search_tokens/app_icons/category_9.png", type:"Lunch"},
			{imgUrl: "https://b.zmtcdn.com/images/search_tokens/app_icons/category_10.png", type:"Dinner"},
			{imgUrl: "https://b.zmtcdn.com/images/search_tokens/app_icons/category_6.png", type:"Cafés"},
			{imgUrl: "https://b.zmtcdn.com/images/search_tokens/app_icons/category_1.png", type:"Delivery"},
			{imgUrl: "https://b.zmtcdn.com/images/search_tokens/app_icons/category_5.png", type:"Take-Away"},
			{imgUrl: "https://b.zmtcdn.com/images/search_tokens/app_icons/category_11.png", type:"Pubs & Bars"},
			{imgUrl: "https://b.zmtcdn.com/images/search_tokens/app_icons/special_10.png", type:"Desserts & Bakes"}]
	}

	function initStatistic(response) {
		var data = response.data;
		if (data.error != "EOK") {
			return;
		}
		vm.stats = data.data;
	}

	vm.gotoCollection = function(featuredId) {
		console.log(featuredId);
		for (k in vm.collections) {
			if (vm.collections[k]['featuredId'] === featuredId) {
				broker.putSingleMessage("collection", vm.collections[k]);
				break;
			}
		}
		$location.path("collection/" + featuredId);
	}
	vm.goto = function(path) {
		$location.path("restaurant/" + path);
	}
	$http.get(baseUrl + "/api/v1/collection/main_page").then(initFeaturedCollections, function(response) {	});
	initQuickSearch();
	$http.get(baseUrl + "/api/v1/restaurant/stats").then(initStatistic, function(response){ console.log(response); });
}