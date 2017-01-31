
function SearchCriteriaController(restaurantService, $routeParams) {
	var vm = this;
	vm.checkedCategory = null;
	vm.currentPage = 0;

	function buildSearchCriteria() {
		var builder = new SearchCriteriaBuilder();
		builder.addQueryKey(vm.checkedCategory);
		builder.addSource(["open_hours", 'phone', 'address', 'name', 'avg_rate', 'cuisines', 'cost_for_2', 'features', 'thumb_img_url']);
		builder.addSearchFields("categories");
		builder.setPage(vm.currentPage);
		return builder.build();
	}


	function searchBriefRestaurants() {
		var criteria = buildSearchCriteria();
		restaurantService.search(criteria, vm.currentPage).then(function(data) {
			window.scrollTo(0, 0);
			data.currentPage = data.currentPage + 1;
			vm.data = data;
		}, function(data) {
		});
	}


	function loadCategoriedRestaurants(cate, page) {
		vm.checkedCategory = cate;
		vm.currentPage = page;
		searchBriefRestaurants();
	}

	vm.clickCategory = function(name) {
		loadCategoriedRestaurants(name, 0);
	}


	restaurantService.loadSearchCompoments()
	.then(function(data) {
		vm.search = data;
	}, function(data) {
	});

	vm.pageChanged = function() {
		loadCategoriedRestaurants(vm.checkedCategory, vm.data.currentPage - 1);
	}

	loadCategoriedRestaurants($routeParams["category"], 0);
}
