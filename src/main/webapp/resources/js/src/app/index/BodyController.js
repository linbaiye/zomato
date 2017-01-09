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
		vm.collections = collectionMap;
		vm.imgUrl = 'https://b.zmtcdn.com/data/collections/6190ad66dfd037c7c4070fc428bfdad5_1481519457.jpg?fit=around%7C300%3A250&crop=300%3A250%3B%2A%2C%2A';
		console.log(vm.collections);
	}

	function initQuickSearch() {
		vm.quickSearches = [
			{imgUrl: "https://b.zmtcdn.com/images/search_tokens/app_icons/category_8.png?output-format=webp", type:"Breakfast"},
			{imgUrl: "https://b.zmtcdn.com/images/search_tokens/app_icons/category_9.png?output-format=webp", type:"Lunch"},
			{imgUrl: "https://b.zmtcdn.com/images/search_tokens/app_icons/category_10.png?output-format=webp", type:"Dinner"},
			{imgUrl: "https://b.zmtcdn.com/images/search_tokens/app_icons/category_6.png?output-format=webp", type:"Cafés"},
			{imgUrl: "https://b.zmtcdn.com/images/search_tokens/app_icons/category_1.png?output-format=webp", type:"Delivery"},
			{imgUrl: "https://b.zmtcdn.com/images/search_tokens/app_icons/category_5.png?output-format=webp", type:"Take-Away"},
			{imgUrl: "https://b.zmtcdn.com/images/search_tokens/app_icons/category_11.png?output-format=webp", type:"Pubs & Bars"},
			{imgUrl: "https://b.zmtcdn.com/images/search_tokens/app_icons/special_10.png?output-format=webp", type:"Desserts & Bakes"}]
	}
	function initStatistic(response) {
		var data = response.data;
		var array = [];
		var k = 0, j = 0;
		for (var i = 0; i < data.length; i++) {
			if (!array[k]) {
				array.push([]);
			}
			array[k][j] = data[i];
			if (++j == 10) {
				++k;
				j = 0;
			}
		}
		vm.statistics = array;
	}

	vm.gotoCollection = function(featuredId) {
		console.log(featuredId);
		for (k in vm.collections) {
			if (vm.collections[k]['featuredId'] === featuredId) {
				broker.putSingleMessage("collection", vm.collections[k]);
				break;
			}
		}
		$location.path("collection/");
	}
	vm.goto = function(path) {
		$location.path("restaurant/" + path);
	}
	$http.get(baseUrl + "/api/v1/collection/main_page").then(initFeaturedCollections, function(response) {	});
	initQuickSearch();
	//$http.get(baseUrl + "/api/v1/restaurant_statistic").then(initStatistic, function(response){ console.log(response); });
}