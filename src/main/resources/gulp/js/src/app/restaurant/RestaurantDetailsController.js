/**
 *
 */
function RestaurantDetailsController(restaurantService, userService, $routeParams, broker, CONFIG, reviewService) {
	var vm = this;
	vm.totalReview = 0;
	vm.loadedReview = 0;
	var REVIEW_NUMBER_PER_LOAD = 5;
	vm.reviews = [];
	restaurantService.loadRestaurantById($routeParams['id'])
	.then(function(data) {
		if (!data.found) {
			return;
		}
		var source = data['_source'];
		source['img_url'] = source['img_url'] || "data:image/jpg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAA4KCw0LCQ4NDA0QDw4RFiQXFhQUFiwgIRokNC43NjMuMjI6QVNGOj1OPjIySGJJTlZYXV5dOEVmbWVabFNbXVn/2wBDAQ8QEBYTFioXFypZOzI7WVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVn/wAARCAAyADIDASIAAhEBAxEB/8QAGwABAAIDAQEAAAAAAAAAAAAAAAECAwQFBgf/xAAkEAACAQMEAgIDAAAAAAAAAAAAAQIDBBEFEiFREyIUUhUxQv/EABgBAQADAQAAAAAAAAAAAAAAAAABAgQF/8QAHREBAAICAgMAAAAAAAAAAAAAAAERAgMSIRMxQf/aAAwDAQACEQMRAD8A+kgjIyuwJBG5dkbo9gWBGV2Ny7AkFdy7AHH/ACD25ya9TUZN8SNWvaV3xFMrCwr8ZTOLGW323xjh9Zq+p1VHEHyaC1e4p1fdvB0vgyUOY8nJvrCvOfrF4Lxs2TK0eNvvWpuOUy1lq87mo4N4Zx6llcQp4jB5KWlpd0qymoM065zvtTOMOPT1m2t9waCq3eF6sG1jel8cekTsj0iwFQi1dkeirpQf7ijIBUItidCm/wCUPBT+qMoJotj8UPqgZAAAAAAAAAAAAH//2Q=="
		vm.restaurant = source;
	}, function(data) {
		console.log(data);
	});

	function loadReviews(from, size) {
		reviewService.loadReviewOfRestaurant($routeParams['id'], from, size)
		.then(function(reviews) {
			if (reviews == null){
				return;
			}
			userService.loadUsersByReviews(reviews.list).then(function(data) {
				vm.reviews = vm.reviews.concat(data);
				vm.totalReview = reviews.total;
				vm.loadedReview = from + size;
			}, function(data) {
			});
		}, function(reviews) {
			console.log(reviews);
		});
	}

	loadReviews(0, REVIEW_NUMBER_PER_LOAD);

	vm.loadMoreReviews = function() {
		loadReviews(vm.loadedReview, REVIEW_NUMBER_PER_LOAD);
	}

	vm.onWritingReviewPending = false;
	vm.onWritingReviewFocus = function() {
		if (vm.onWritingReviewPending) {
			return;
		}
		var promise = broker.requestCooperation(CONFIG.CONTROLLOR_NAMES.HEADER,
			{opcode: CONFIG.OPCODE.LOGIN, data: "Please login to comment."});
			if (!promise) {
				vm.onWritingReviewPending = false;
				return;
			}
			vm.onWritingReviewPending = true;
			promise.then(function(ret){
				vm.onWritingReviewPending = false;
				vm.isWritingReview = true;
			}, function(ret) {
				vm.onWritingReviewPending = false;
			});
		}
	}
