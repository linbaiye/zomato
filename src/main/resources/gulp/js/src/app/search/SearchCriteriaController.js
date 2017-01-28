
function SearchCriteriaController(restaurantService, $routeParams) {
	var vm = this;

	function loadCategoriedRestaurants(cateId, page) {
		if (page < 0) {
			page = 0;
		}
		restaurantService.getRestuarantsByCategory(cateId, page)
		.then(function(data) {
			window.scrollTo(0, 0);
			vm.data = data;
		}, function(data) {
		});
	}
	vm.selectedCategories = {};

	vm.clickCategory = function(name) {
		if (vm.selectedCategories[name]) {
			delete vm.selectedCategories[name];
		} else {
			vm.selectedCategories[name] = true;
		}
		console.log(vm.selectedCategories);
	}


	restaurantService.loadSearchCompoments()
	.then(function(data) {
		vm.search = data;
	}, function(data) {
	});

	loadCategoriedRestaurants($routeParams["categoryId"], $routeParams["page"] - 1);
	vm.pageChanged = function() {
		loadCategoriedRestaurants($routeParams["categoryId"], vm.data.currentPage - 1);
	}
}
