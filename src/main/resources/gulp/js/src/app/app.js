(function() {
	angular.module("app", [ "ui.bootstrap", "ngRoute", "angularModalService", "ngSanitize"])
	.constant("baseUrl", window.location.origin + window.location.pathname)
	.service("broker", BrokerService)
	.service("util", UtilService)
	.service("userService", UserService)
	.service("restaurantService", ["$http", "$q", "baseUrl", RestaurantService])
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
		.when("/search/category/:category", {
			templateUrl: "static/html/search.html"
		})
		.when("/search/location/:location", {
			templateUrl: "static/html/search.html"
		});
	}])
	.controller("BodyController", ["$http", "$location", "baseUrl", "broker", "util", BodyController])
	.controller("CollectionController", ["$location", "$http", "$routeParams", "broker", "baseUrl", CollectionController])
	.controller("HeaderController", ["ModalService", HeaderController])
	.controller("SearchCriteriaController", ["restaurantService", "$routeParams", "util", "ModalService", "$location", SearchCriteriaController])
	.controller("RecommandRestaurantController", ["restaurantService", "util", RecommandRestaurantController])
	.controller("RestaurantDetailsController", ["restaurantService", "userService", "$routeParams", RestaurantDetailsController])
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
	}).filter("joinNameOfList", function() {
		return function(list) {
			if (!(list instanceof Array)) {
				return list;
			}
			var token = [];
			for (var i = 0; i < list.length; i++) {
				if (list[i].type) {
					token.push(list[i].type);
					continue;
				}
				if (list[i].name) {
					token.push(list[i].name);
				}
			}
			return token.join(", ");
		}
	}).filter("rateClass", function() {
		return function(rate) {
			if (!rate) {
				return "nolevel";
			}
			for (var i = 1; i <= 6; i++) {
				if (rate.toFixed(2) < (2 + (i * 0.5))) {
					return "level" + i;
				}
			}
			return "level6";
		}
	}).filter("shortenDesc", function() {
		return shortenFunction(65);
	}).filter("getDistrict", function() {
		return function(address) {
			if (!address || typeof address != "string") {
				return;
			}
			var token = address.split(",");
			return token.length >= 3 ? token[token.length - 3] : "";
		}
	});
}());
