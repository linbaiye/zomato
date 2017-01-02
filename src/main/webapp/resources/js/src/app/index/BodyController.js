/**
 * 
 */

function BodyController($http, $location, baseUrl) {
	var vm = this;
	function initRecommendation(response) {
		var data = response.data;
		var recommendation = [[], []];
		var i = 0, j = 0;
		var desc = {
			dessert: "Desserts you should try",
			dining: "Have delicious dinner after a day's work",
			cafe: "Who does not like cafe",
			pub: "Go for a drink with your friends",
			fast: "Fast food for a busy day",
			magic: "Buy yourself a lottery if you find this excellent"
		}
		var type = {
			dessert: "Dessert Parlour",
			pub: "Beer and wine",
			cafe: "Café",
			fast: "Fast Food",
			dining: "Casual Dining",
			magic: "Magical recommendation"
		};
		for (var k = 0; k < data.length; k++) {
			recommendation[i][j] = {id: data[k]['id'], "type": type[data[k]['name']], "style":{'background-image': 'url(' + data[k]['url'] + ')'}, "desc": desc[data[k]['name']]};
			if (++j == 3) {
				++i;
				j = 0;
			}
		}
		vm.recommendation = recommendation;
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

	vm.goto = function(path) {
		$location.path("restaurant/" + path);
	}
	$http.get(baseUrl + "/api/v1/recommendation").then(initRecommendation, function(response) {	});
	initQuickSearch();
	$http.get(baseUrl + "/api/v1/restaurant_statistic").then(initStatistic, function(response){ console.log(response); });
}