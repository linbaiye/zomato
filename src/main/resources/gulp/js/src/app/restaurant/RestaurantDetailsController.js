/**
 *
 */
function RestaurantDetailsController(restaurantService, userService, $routeParams, broker, CONFIG, reviewService) {
	var vm = this;
	vm.totalReview = 0;
	vm.loadedReview = 0;
	var REVIEW_NUMBER_PER_LOAD = 5;
	vm.reviews = [];
	vm.reviewButtonLabel = "Publish Review";
	vm.rate = {
		max: 9,
		current: 0,
		transformedRate: 0,
		class: "",
		labelClass: "",
		overStar: 0
	};

	function calculateRateClass(rate) {
		if (rate == 0) {
			return 0;
		}
		for (var i = 1; i <= 6; i++) {
			if (rate.toFixed(2) < (2 + (i * 0.5))) {
				return  i;
			}
		}
		return 6;
	}

	function transformRate(value) {
		return (value > 1) ? (value - 1) / 2 + 1 : value;
	}

	vm.onRateLeave = function() {
		vm.rate.transformedRate = transformRate(vm.rate.current);
		vm.rate.class = "rate-level" + calculateRateClass(vm.rate.transformedRate);
		vm.rate.labelClass = "level" + calculateRateClass(vm.rate.transformedRate);
		vm.rate.overStar = vm.rate.transformedRate;
	}

	vm.rate.class = "rate-level" + calculateRateClass(transformRate(vm.rate.current));
	vm.rate.labelClass = "level" + calculateRateClass(transformRate(vm.rate.overStar));

	vm.onRateHover = function(value) {
		vm.rate.overStar = transformRate(value);
		vm.rate.class = "rate-level" + calculateRateClass(vm.rate.overStar);
		vm.rate.labelClass = "level" + calculateRateClass(vm.rate.overStar);
	};

	function showPublishReviewTip(cls, text) {
		vm.publishReviewResult = {
			class: cls,
			text: text
		};
	}

	vm.onCancelWritingReview = function() {
		vm.isWritingReview = false;
		vm.publishReviewResult = null;
	}

	vm.onPublishReview = function() {
		if (!vm.newReview || vm.newReview.length < 140) {
			vm.reviewInvalid = true;
			showPublishReviewTip("error-message", "Review's length must be longer than 140 characters.");
			return;
		}
		if (!userService.isAuthed()) {
			showPublishReviewTip("error-message", "Please login to publish your review.");
			return;
		}
		vm.reviewButtonLabel = '<i class="fa mobile-icon fa-spinner fa-pulse fa-fw"></i>';
		reviewService.publishReview($routeParams['id'], vm.rate.transformedRate, vm.newReview)
		.then(function(data) {
			vm.reviewButtonLabel = "Publish Review";
			if (data.error == "EOK") {
				showPublishReviewTip("success-message", "You review has been published successfully, please refresh to see your comment.");
			} else {
				showPublishReviewTip("error-message", "Oops, something went wrong, please retry later.");
			}
		},
		function(data) {
			vm.reviewButtonLabel = "Publish Review";
			showPublishReviewTip("error-message", "Oops, something went wrong, please retry later.");
		});
	}

	restaurantService.loadRestaurantById($routeParams['id'])
	.then(function(data) {
		if (!data.found) {
			return;
		}
		console.log(data);
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
		vm.reviewInvalid = false;
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
