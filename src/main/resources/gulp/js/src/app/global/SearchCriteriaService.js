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

	this.loadAllFeaturedCollections = function() {
		return this.commitPromiseV2(CONFIG.BASE_URL + "api/v1/feature/all", null, function(response) {
			if (response.error !== "EOK") {
				return response;
			}
			var data = response.data;
			for (var i = 0; i < data.length; i++) {
				var tmp = (i + 1) % 3;
				data[i]["col"] = "col" + (tmp == 0 ? "3" : tmp);
			}
			return response;
		});
	}
}
