/**
 *
 */
function HeaderController(modalService) {
	var vm = this;
	vm.onClickLogin = function() {
		modalService.showModal({
			templateUrl: 'login.html',
			controller: function($scope, close) {
				$scope.close = function(result) {
					close(result, 400);
				}
			},
			controllerAs : "modal"
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result) {

			});
		})
	}
}
