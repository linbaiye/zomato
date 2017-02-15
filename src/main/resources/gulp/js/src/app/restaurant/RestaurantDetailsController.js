/**
 *
 */
function RestaurantDetailsController(restaurantService, userService, $routeParams) {
	var vm = this;
	restaurantService.loadRestaurantById($routeParams['id'])
	.then(function(data) {
		if (!data.found) {
			return;
		}
		var source = data['_source'];
		source['img_url'] = source['img_url'] || "data:image/jpg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAA4KCw0LCQ4NDA0QDw4RFiQXFhQUFiwgIRokNC43NjMuMjI6QVNGOj1OPjIySGJJTlZYXV5dOEVmbWVabFNbXVn/2wBDAQ8QEBYTFioXFypZOzI7WVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVn/wAARCAAyADIDASIAAhEBAxEB/8QAGwABAAIDAQEAAAAAAAAAAAAAAAECAwQFBgf/xAAkEAACAQMEAgIDAAAAAAAAAAAAAQIDBBEFEiFREyIUUhUxQv/EABgBAQADAQAAAAAAAAAAAAAAAAABAgQF/8QAHREBAAICAgMAAAAAAAAAAAAAAAERAgMSIRMxQf/aAAwDAQACEQMRAD8A+kgjIyuwJBG5dkbo9gWBGV2Ny7AkFdy7AHH/ACD25ya9TUZN8SNWvaV3xFMrCwr8ZTOLGW323xjh9Zq+p1VHEHyaC1e4p1fdvB0vgyUOY8nJvrCvOfrF4Lxs2TK0eNvvWpuOUy1lq87mo4N4Zx6llcQp4jB5KWlpd0qymoM065zvtTOMOPT1m2t9waCq3eF6sG1jel8cekTsj0iwFQi1dkeirpQf7ijIBUItidCm/wCUPBT+qMoJotj8UPqgZAAAAAAAAAAAAH//2Q=="
		vm.restaurant = source;
		userService.loadUsersByIds(source['reviews'])
		.then(function(reviews) {
			if (reviews == null){
				return;
			}
			vm.reviews = reviews;
		}, function(reviews) {
			console.log(reviews);
		});
	}, function(data) {
		console.log(data);
	});
}
