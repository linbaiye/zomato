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
			vm.currentCollection = data.data;
		} , function(response){});
	} else {
		
	}
	vm.gotoRestaurant = function(path) {
		$location.path("restaurant/" + path);
	}
}