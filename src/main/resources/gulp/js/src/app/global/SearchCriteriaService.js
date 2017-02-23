function SearchCriteriaService($http, $q, CONFIG) {
	HttpPromiseSerivce.call(this, $http, $q);
  this.loadFeaturedCollectionOfIndexPage = function() {
		return this.commitPromiseV2(CONFIG.BASE_URL + "api/v1/feature/main_page");
  }

  this.loadCategories = function() {
		return this.commitPromiseV2(CONFIG.BASE_URL + "api/v1/category");
  }

  this.loadPlaceStats = function() {
		return this.commitPromiseV2(CONFIG.BASE_URL + "api/v1/restaurant/stats");
  }
}
