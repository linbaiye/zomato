/**
 * 
 */
function CollectionController($location, $http, $routeParams, broker, baseUrl) {
	var vm = this;
	if ($routeParams["id"] != "all") {
		$http.get(baseUrl + "/api/v1/restaurant/feature/" + $routeParams["id"]).then(function(response) {
			var data = response.data;
			if (data.error !== "EOK") {
				return;
			}
			var fn = shortenFunction(40);
			var rl = data.data["restaurantSet"];
			for (var i = 0; i < rl.length; i++) {
				rl[i]["shortName"] = fn(rl[i]["name"]);
			}
			vm.currentCollection = data.data;
		} , function(response){});
	}
	$http.get(baseUrl + "/api/v1/feature/all").then(function(response) {
		var data = response.data;
		if (data.error !== "EOK") {
			return;
		}
		for (var i = 0; i < data.data.length; i++) {
			var tmp = (i + 1) % 3;
			data.data[i]["col"] = "col" + (tmp == 0 ? "3" : tmp);
		}
		vm.collections = data.data;
	}, function(response){});
	
	vm.gotoRestaurant = function(path) {
		$location.path("restaurant/" + path);
	}
	vm.gotoCollection = function(featured) {
		$location.path("feature/" + featured);
	}
}