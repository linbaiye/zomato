
function SearchCriteriaController(restaurantService, $routeParams) {
	var vm = this;
	vm.filtersStatus = 'filters-collapsed';
	vm.filterStatus = 'filter-collapsed';
	vm.checkedCategory = null;
	vm.checkedCuisine = null;
	vm.checkedLocation = null;

	function buildSearchCriteria(pageToGo) {
		var builder = new SearchCriteriaBuilder();
		builder.setPage(pageToGo);
		if (vm.checkedCuisine) {
			builder.addFilterTerms([{"cuisines": vm.checkedCuisine}]);
		}
		if (vm.checkedCategory) {
			builder.addFilterTerms([{"categories": vm.checkedCategory}]);
		}
		if (vm.checkedLocation) {
			builder.addNestedMustPhraseMatch("address", "text_address", vm.checkedLocation);
		}
		builder.addSources(["open_hours", 'phone', 'address', 'name', 'avg_rate', 'cuisines', 'cost_for_2', 'features', 'thumb_img_url']);
		return builder.build();
	}

	function searchBriefRestaurants(pageToGo) {
		var criteria = buildSearchCriteria(pageToGo);
		vm.spinner = true;
		restaurantService.search(criteria, pageToGo).then(function(data) {
			window.scrollTo(0, 0);
			data.currentPage = data.currentPage + 1;
			vm.data = data;
			vm.spinner = false;
		}, function(data) {
			vm.spinner = false;
		});
	}


	function loadCategoriedRestaurants(cate) {
		vm.checkedCategory = cate;
		searchBriefRestaurants(0);
	}

	vm.clickCategory = function(name) {
		loadCategoriedRestaurants(name);
	}

	vm.clickCuisine = function(name) {
		vm.checkedCuisine = (vm.checkedCuisine == name) ? null : name;
		searchBriefRestaurants(0);
	}

	vm.clickLocation = function(location) {
		vm.checkedLocation = (vm.checkedLocation == location) ? null : location;
		searchBriefRestaurants(0);
	}

	restaurantService.loadSearchCompoments()
	.then(function(data) {
		vm.search = data;
	}, function(data) {
	});

	vm.pageChanged = function() {
		searchBriefRestaurants(vm.data.currentPage - 1);
	}

	vm.toggleFiltersClass = function() {
		vm.filtersStatus = (vm.filtersStatus == 'filters-open') ? 'filters-collapsed' : 'filters-open';
	}
	loadCategoriedRestaurants($routeParams["category"]);
}
