function loginModalController($scope, $element, userService, close) {
  var vm = $scope;
  vm.close = function(result) {
    close(result, 400);
  }
  vm.isUsernameValid = true;
  vm.isPasswordValid = true;
  vm.doLogin = function() {
    vm.isUsernameValid = vm.loginForm.username.$valid;
    vm.isPasswordValid = vm.loginForm.password.$valid;
    if (!vm.isUsernameValid || !vm.isPasswordValid || vm.isAuthing) {
      return;
    }
    vm.isAuthing = true;
    userService.doAuth(vm.username, vm.password)
    .then(function(data) {
      vm.isAuthing = false;
      if (data.error == "EOK") {
        $element.modal('hide');
        vm.close();
      } else {
        vm.errorMessage = "Bad credentials, please make sure your username and password are correct.";
      }
    }, function(data) {
      vm.isAuthing = false;
      vm.errorMessage = "Bad credentials, please make sure your username and password are correct.";
    });
  }
}
