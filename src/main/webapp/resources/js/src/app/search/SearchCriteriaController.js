
function SearchCriteriaController(restaurantService, $routeParams) {
	var vm = this;
	
	function loadCategoriedRestaurants(cateId, page) {
		if (page < 0) {
			page = 0;
		}
		restaurantService.getRestuarantsByCategory(cateId, page)
		.then(function(data) {
			vm.data = data;
		}, function(data) {
			console.log(data);
		});	
	}

	restaurantService.loadSearchCompoments()
	.then(function(data) {
		vm.search = data;
	}, function(data) {
		console.log(data);
	});	

	loadCategoriedRestaurants($routeParams["categoryId"], $routeParams["page"] - 1)
	vm.pageChanged = function() {
		loadCategoriedRestaurants($routeParams["categoryId"], vm.data.currentPage - 1);
	}
}
