/*
 * There must have been some kind of strategy to manage recommanded restaurants,
 * don't wanna do reverse enginnering.
 */
function RecommandRestaurantController(restaurantService, util) {
  var vm = this;
  var textMap = {
    "cafe": {
      "title": "Visit a café",
      "desc": "Café"
    },
    "dinner": {
      "title": "Go out for a meal",
      "desc": "Dine-out restaurants"
    },
    "take-away": {
      "title": "Grab food to-go",
      "desc": "Takeaway restaurants"
    }
  }
  //restaurantService

  restaurantService.getRecommandRestaurants().then(function(data) {
    for (var k in data) {
      data[k] = {
        "restaurantList": util.to2DArray(data[k], 3),
        "text": textMap[k]
      };
    }
    vm.data = data;
  }, function(data) {
    console.log("error");
  });
}
