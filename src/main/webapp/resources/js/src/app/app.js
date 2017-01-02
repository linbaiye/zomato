/**
 * 
 */

(function() {
	angular.module("app", [ "ui.bootstrap", "ngRoute"])
	.constant("baseUrl", window.location.origin + window.location.pathname)
	.config(function($routeProvider) {
		$routeProvider.when("/", {
			templateUrl: "static/html/index.html"
		})
		.when("/restaurant/:id", {
			templateUrl: "static/html/restaurant.html"
		})
	})
	.controller("BodyController", BodyController)
	.controller("RestaurantInfoController", RestaurantInfoController)
	.controller("HeaderController", HeaderController);
}());
