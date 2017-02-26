/**
 *
 */
function CollectionController($location, $routeParams, searchCriteriaService, restaurantService) {
	var vm = this;
	if ($routeParams["id"] != "all") {
		restaurantService.loadFeaturedBriefRestaurants($routeParams["id"])
		.then(function(response) {
			if (response.error !== "EOK") {
				return;
			}
			vm.currentCollection = response.data;
		}, function(response) {
			console.log(response);
		});
	}

	searchCriteriaService.loadAllFeaturedCollections()
	.then(function(response) {
		if (response.error !== "EOK") {
			return;
		}
		vm.collections = response.data;
	}, function(response) {
		console.log(response);
	});
	vm.gotoRestaurant = function(path) {
		$location.path("restaurant/" + path);
	}
	vm.gotoCollection = function(featured) {
		$location.path("feature/" + featured);
	}
}
