
function ReviewService($http, $q, baseUrl) {
	HttpPromiseSerivce.call(this, $http, $q);
  this.loadReviewOfRestaurant = function(restaurantId, from, size) {
		var builder = new SearchCriteriaBuilder();
    builder.setParent(restaurantId, 'restaurant');
    builder.setFromAndSize(from, size);
		return this.commitPromiseV2(baseUrl + "api/v1/search/compound/review", builder.build(), function(data) {
      if (!data.hits || !data.hits.hits || data.hits.hits.length <= 0) {
        return null;
      }
      var result = [];
      var hits = data.hits.hits;
      for (var i = 0; i < hits.length; i++) {
        result.push(hits[i]['_source']);
      }
      return {
        list: result,
        total: data.hits.total
      }
    });
  }
}
