
function SearchCriteriaController(restaurantService, $routeParams, util, modalService, $location) {
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

	function showError() {
		modalService.showModal({
			templateUrl: 'error.html',
			controller: function($scope, close) {
				$scope.close = function(result) {
					close(result, 400);
				}
			}
		}).then(function(modal) {
			modal.element.modal();
		});
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
			showError();
		});
	}

	vm.clickCategory = function(name) {
		vm.checkedCategory = (vm.checkedCategory == name) ? null : name;
		searchBriefRestaurants(0);
	}

	vm.clickCuisine = function(item) {
		vm.search.first10Cuisines = util.moveItemToHead(item, vm.search.first10Cuisines, 'name');
		vm.checkedCuisine = (vm.checkedCuisine == item.name) ? null : item.name;
		searchBriefRestaurants(0);
	}

	vm.clickLocation = function(item) {
		vm.search.first10Places = util.moveItemToHead(item, vm.search.first10Places, 'district');
		vm.checkedLocation = (vm.checkedLocation == item.district) ? null : item.district;
		searchBriefRestaurants(0);
	}

	restaurantService.loadSearchCompoments()
	.then(function(data) {
		vm.search = data;
	}, function(data) {
			showError();
	});


	vm.gotoRestaurant = function(id) {
		$location.path("restaurant/" + id);
	}

	vm.pageChanged = function() {
		searchBriefRestaurants(vm.data.currentPage - 1);
	}

	vm.openModal = function(type) {
		modalService.showModal({
			templateUrl: 'modal.html',
			controller: function($scope, close) {
				$scope.close = function(result) {
					close(result, 400);
				}
			},
			controllerAs : "modal"
		}).then(function(modal) {
			if (type == 'cuisine') {
				modal.controller.itemListOfModal = vm.search.cuisineList;
			} else if (type == 'location') {
				modal.controller.itemListOfModal = vm.search.placeList;
			}
    	modal.element.modal();
      modal.close.then(function(result) {
				if (type == "cuisine" && result) {
					vm.clickCuisine(result);
				} else if (type == "location" && result) {
					vm.clickLocation(result);
				}
      });
    });
	}

	vm.toggleFiltersClass = function() {
		vm.filtersStatus = (vm.filtersStatus == 'filters-open') ? 'filters-collapsed' : 'filters-open';
		vm.isCol3Hidden = true;
	}
	vm.checkedCategory = $routeParams["category"];
	vm.checkedLocation = $routeParams["location"];
	searchBriefRestaurants(0);
}
