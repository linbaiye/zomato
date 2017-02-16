/**
 *
 */
function HeaderController(modalService, userService, CONFIG, $q, broker) {
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

	vm.showModal = function(cb, title) {
		modalService.showModal({
			templateUrl: 'login.html',
			controller: 'LoginModalController',
			controllerAs : "vm"
		}).then(function(modal) {
			modal.element.modal();
			modal.scope.title = title;
			modal.close.then(function() {
				vm.status = userService.isAuthed() ? "authed" : "notAuthed";
				cb &&	cb();
			});
		});
	}

	vm.onClickLogin = function() {
		if (userService.isAuthed()) {
			vm.doLogout();
			return;
		}
		vm.showModal();
	}

	vm.onCooperation = function(operation) {
		if (!operation || typeof operation != "object") {
			return null;
		}
		if (operation['opcode'] == CONFIG.OPCODE.LOGIN) {
    	var deferred = $q.defer();
			if (userService.isAuthed()) {
				deferred.resolve(true);
			} else {
				vm.showModal(function() {
					if (userService.isAuthed()) {
						deferred.resolve(true);
					} else {
						deferred.reject(false);
					}
				}, operation['data']);
			}
			return deferred.promise;
		}
		return null;
	}

	broker.acceptCooperation(CONFIG.CONTROLLOR_NAMES.HEADER, vm.onCooperation);
}
