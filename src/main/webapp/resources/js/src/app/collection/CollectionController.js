/**
 * 
 */
function CollectionController($scope, $location, $http, broker) {
	var vm = this;
	vm.gotoRestaurant = function(path) {
		$location.path("restaurant/" + path);
	}
	console.log(broker.getSingleMessage("collection"));
}