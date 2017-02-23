function BodyController($location, utilService, searchcriteriaService) {
	var vm = this;

	vm.gotoLocation = function(location) {
		$location.path("search/location/" + location);
	}

	vm.gotoCollection = function(featured) {
		$location.path("feature/" + featured);
	}

	vm.gotoCategorySearch = function(category) {
		$location.path("search/category/" + category);
	}

	searchcriteriaService.loadFeaturedCollectionOfIndexPage()
	.then(function(response) {
		if (!response || response.error !== "EOK" || !(response.data instanceof Array)) {
			vm.collections = [{type: "Ops, something went wrong."}];
			return;
		}
		vm.collections = response.data;
		vm.imgUrl = 'https://b.zmtcdn.com/data/collections/6190ad66dfd037c7c4070fc428bfdad5_1481519457.jpg?fit=around%7C300%3A250&crop=300%3A250%3B%2A%2C%2A';
	}, function(response) {
		vm.collections = [{type: "Ops, something went wrong."}];
	});

	searchcriteriaService.loadCategories()
	.then(function(response) {
		if (!response || response.error !== "EOK" || !(response.data instanceof Array)) {
			return;
		}
		vm.quickSearches = utilService.to2DArray(response.data, 2);
	}, function(response) {} );

	searchcriteriaService.loadPlaceStats()
	.then(function(response) {
		if (!response || response.error !== "EOK" || !(response.data instanceof Array)) {
			return;
		}
		vm.stats = response.data;
	}, function(response) {});
}
