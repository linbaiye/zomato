function HttpPromiseSerivce($http, $q) {
  function _commitRequest(deferred, url, callback, data, onRequestOkCb) {
    function onRequestError(response) {
      deferred.reject(response.data);
      return deferred.promise;
    }
    if (!data) {
      return $http.get(url).then(onRequestOkCb, onRequestError);
    }
    if (typeof data == "string") {
      var headers = {'Content-Type': 'application/x-www-form-urlencoded'}
      var postData = unescape(encodeURIComponent(data));
    } else {
      var headers = {'Content-Type': 'Application/json'}
      var postData = unescape(encodeURIComponent(JSON.stringify(data)));
    }
    return $http.post(url, postData, {headers: headers})
    .then(onRequestOkCb, onRequestError);
  }

  this.commitPromise = function(url, callback, data) {
    var deferred = $q.defer();
    function onRequestOk(response) {
      if (response.data.error != "EOK") {
        deferred.reject(response.data);
      } else {
        if (callback) {
          deferred.resolve(callback(response.data.data));
        } else {
          deferred.resolve(response.data.data);
        }
      }
      return deferred.promise;
    }
    return _commitRequest(deferred, url, callback, data, onRequestOk);
  }


  this.commitPromiseV2 = function(url, data, callback) {
    if (typeof url != "string") {
      throw "Invalid http request.";
    }
    var deferred = $q.defer();
    function onRequestOk(response) {
      if (callback) {
        deferred.resolve(callback(response.data));
      } else {
        deferred.resolve(response.data);
      }
      return deferred.promise;
    }
    return _commitRequest(deferred, url, callback, data, onRequestOk);
  }
}
