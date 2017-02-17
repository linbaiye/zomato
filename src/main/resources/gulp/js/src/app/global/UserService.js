function UserService($http, $q, baseUrl, $cookies) {
  HttpPromiseSerivce.call(this, $http, $q);

  function extractIds(reviews) {
    var ids = [];
    for (var i = 0; i < reviews.length; i++) {
      ids.push(reviews[i]['user_id']);
    }
    return ids;
  }

  this.loadUsersByReviews = function(reviews) {
    if (!(reviews instanceof Array) || reviews.length <= 0) {
      return null;
    }
    var ids = extractIds(reviews);
		return this.commitPromiseV2(baseUrl + "/api/v1/user/list", ids, function(data) {
      if (data.error != "EOK") {
        return null;
      }
      var newReviews = reviews.slice(0);
      for (var i = 0; i < newReviews.length; i++) {
        newReviews[i].epoch = new Date(newReviews[i].review_time).getTime();
        for (var j = 0; j < data.data.length; j++) {
          if (newReviews[i]['user_id'] == data.data[j]['id']) {
            newReviews[i]['user'] = data.data[j];
          }
        }
      }
      return newReviews;
    });
  }
  this.doAuth = function(username, password) {
    if (typeof username != "string" || typeof password != "string") {
      throw "Bad credentials passed.";
    }
    return this.commitPromiseV2(baseUrl + "/j_spring_security_check", "username=" + username + "&password=" + password);
  }
  this.isAuthed = function() {
    return $cookies.get('isAuthed');
  }
  this.logout = function() {
    return this.commitPromiseV2(baseUrl + "/j_spring_security_logout");
  }
}
