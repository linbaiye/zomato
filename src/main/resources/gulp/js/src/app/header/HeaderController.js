/**
 *
 */
function HeaderController(modalService, userService) {
	var vm = this;
	vm.statusLabel = {authed: 'Log out', notAuthed: 'Log in', pending: '<i class="fa fa-spinner fa-pulse fa-fw"></i>'};
	vm.mobileStatusLabel = {
		notAuthed: '<i class="fa fa-user-circle-o mobile-icon" aria-hidden="true"></i>',
		authed: '<i class="fa mobile-icon fa-sign-out" aria-hidden="true"></i>',
		pending: '<i class="fa mobile-icon fa-spinner fa-pulse fa-fw"></i>'
	};
	vm.status = userService.isAuthed() ? "authed" : "notAuthed";
	vm.doLogout = function() {
		vm.status = "pending";
		userService.logout()
		.then(function(data) {
			vm.status = userService.isAuthed() ? "authed" : "notAuthed";
		}, function(data) {
			vm.status = userService.isAuthed() ? "authed" : "notAuthed";
		});
	}
	vm.onClickLogin = function() {
		if (userService.isAuthed()) {
			vm.doLogout();
			return;
		}
		modalService.showModal({
			templateUrl: 'login.html',
			controller: 'LoginModalController',
			controllerAs : "vm"
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function() {
				vm.status = userService.isAuthed() ? "authed" : "notAuthed";
			});
		})
	}
}
