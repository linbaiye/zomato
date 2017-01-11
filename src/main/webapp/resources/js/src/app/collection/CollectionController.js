/**
 * 
 */
function CollectionController($scope, $location, $http, $routeParams, broker, baseUrl) {
	var vm = this;
	function onSuccess(response) {
		console.log(response);
		var data = response.data;
		if (data.error !== "EOK") {
			return;
		}
		vm.currentCollection = data.data;
	}
	//var tmp = broker.getSingleMessage("collection");
	$http.get(baseUrl + "/api/v1/restaurant/collection/" + $routeParams["id"]).then(onSuccess,
				function(response){});
	//var id = $routeParams["id"];

	vm.gotoRestaurant = function(path) {
		$location.path("restaurant/" + path);
	}
}