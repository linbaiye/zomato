(function() {
	angular.module("app", [ "ui.bootstrap", "ngRoute"])
	.service("broker", BrokerService)
	.constant("baseUrl", window.location.origin + window.location.pathname)
	.config(["$routeProvider", function($routeProvider) {
		$routeProvider.when("/", {
			templateUrl: "static/html/index.html"
		})
		.when("/restaurant/:id", {
			templateUrl: "static/html/restaurant.html"
		})
		.when("/feature/:id", {
			templateUrl: "static/html/collection.html"
		})
	}])
	.controller("BodyController", ["$http", "$location", "baseUrl", "broker", BodyController])
	.controller("CollectionController", ["$location", "$http", "$routeParams", "broker", "baseUrl", CollectionController])
	.controller("HeaderController", HeaderController)
	.filter("joinDistrictAndCity", function() {
		return function(address) {
			var token = address.split(",");
			if (token[token.length - 1].trim() == "NSW" && token.length >= 2) {
				token.splice(token.length - 1, 1);
			}
			if (token.length > 2) {
				return token[token.length - 2].trim() + ", " + token[token.length - 1];
			}
			return token.join(",");
		}
	}).filter("joinCuisine", function() {
		return function(list) {
			if (!(list instanceof Array)) {
				return list;
			}
			var token = [];
			for (var i = 0; i < list.length; i++) {
				if (list[i].name) {
					token.push(list[i].name);
				}
			}
			return token.join(", ");
		}
	});
}());
