function UserService($http, $q, baseUrl) {
  HttpPromiseSerivce.call(this, $http, $q);
  this.loadUsersByIds = function(reviews) {
    if (!reviews || !(reviews instanceof Array) || reviews.length <= 0) {
      return;
    }
    var ids = [];
    for (var i = 0; i < reviews.length; i++) {
      ids.push(reviews[i]['user_id']);
    }
		return this.commitPromiseV2(baseUrl + "/api/v1/user/list", function(data) {
      if (data.error != "EOK") {
        return null;
      }
      var newReviews = reviews.slice(0);
      for (var i = 0; i < newReviews.length; i++) {
        for (var j = 0; j < data.data.length; j++) {
          if (newReviews[i]['user_id'] == data.data[j]['id']) {
            newReviews[i]['user'] = data.data[j];
          }
        }
      }
      return newReviews;
    }, ids);
  }
}
