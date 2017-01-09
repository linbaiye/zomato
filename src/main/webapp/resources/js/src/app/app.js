/**
 * 
 */

(function() {
	angular.module("app", [ "ui.bootstrap", "ngRoute"])
	.service("broker", BrokerService)
	.constant("baseUrl", window.location.origin + window.location.pathname)
	.config(function($routeProvider) {
		$routeProvider.when("/", {
			templateUrl: "static/html/index.html"
		})
		.when("/restaurant/:id", {
			templateUrl: "static/html/restaurant.html"
		})
		.when("/collection/", {
			templateUrl: "static/html/collection.html"
		})
	})
	.controller("BodyController", BodyController)
	.controller("RestaurantInfoController", RestaurantInfoController)
	.controller("HeaderController", HeaderController)
	.controller("CollectionController", CollectionController);
}());
