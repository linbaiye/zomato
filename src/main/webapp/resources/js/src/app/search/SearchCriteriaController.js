
function SearchCriteriaController($http, $routeParams, baseUrl) {
	var vm = this;
	
	function initSearchPanel(response) {
		var data = response.data;
		if (data.error !== "EOK") {
			return;
		}
		vm.categoryList = data.data.categoryList;
		/* The cuisineList is too long to display, show part of it and display it fully on demand. */
		var cusinesToShow = [];
		for (var i = 0; i < 10; i++) {
			cusinesToShow[i] = data.data.cuisineList[i];
		}
		for (var i = 0; i < data.data.restaurantList.length; i++) {
			var addr = data.data.restaurantList[i].address;
			var token = addr.textAddress.split(",");
			addr["district"]  = token[token.length - 3];
			var cost = /.+A\$([.\d]+).*/g.exec(data.data.restaurantList[i].approximatePrice);
			data.data.restaurantList[i].costForTwo =  (!cost || cost.length < 2) ? "" : cost[1];
		}
		vm.cusines = cusinesToShow;
		vm.restaurantList = data.data.restaurantList;
		console.log(vm.restaurantList);
	}
	
	$http.get(baseUrl + "/api/v1/search/criteria/" + $routeParams["id"]).then(initSearchPanel, function() {});
}
