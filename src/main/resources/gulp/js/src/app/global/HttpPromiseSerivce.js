function HttpPromiseSerivce($http, $q) {
  function _commitRequest(deferred, url, callback, data, onRequestOkCb) {
    function onRequestError(response) {
      deferred.reject(response.data);
      return deferred.promise;
    }
    if (!data) {
      return $http.get(url).then(onRequestOkCb, onRequestError);
    }
    return $http.post(url, unescape(encodeURIComponent(JSON.stringify(data))), {headers: {'Content-Type': 'Application/json'}})
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


  this.commitPromiseV2 = function(url, callback, data) {
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
