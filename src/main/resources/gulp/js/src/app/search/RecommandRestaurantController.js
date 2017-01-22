/*
 * There must have been some kind of strategy to manage recommanded restaurants,
 * don't wanna do reverse enginnering.
 */
function RecommandRestaurantController(restaurantService) {
  //restaurantService

  restaurantService.getRecommandRestaurants(function(data) {
    console.log("ok");
  }, function(data) {

  });
}
