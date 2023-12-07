var host = "http://localhost:8080";
app.controller("productdetailCtrl", function($scope, $http, $timeout, $routeParams) {
	var productId = $routeParams.productId;
	$scope.product = {};
	$scope.star = [];
	$scope.formattedEndDay = null;
	$scope.listProductByCategory = [];
	$http.get(host + "/rest/product/" + productId)
		.then(function(res) {
			$scope.product = res.data;
			$scope.star = $scope.product.star;
			$http.get(host + "/rest/product-by-category/" + res.data.category.categoryId)
				.then(function(respone) {
					$scope.listProductByCategory = respone.data;

				})
				.catch(function(err) {
					console.log(err);
				})
		})
		.catch(function(err) {
			console.log(err);
		})
	$http.get(host + "/rest/flash-sale-active")
		.then(function(resp) {
			$scope.flashSaleActive = resp;
			$scope.flashSaleActive.endDay = new Date($scope.flashSaleActive.endDay);
			$scope.flashSaleActive.startDay = new Date(
				$scope.flashSaleActive.startDay
			);
			var startDay = $scope.flashSaleActive.startDay;
			var endDay = $scope.flashSaleActive.data.endDay;
			$scope.nowDay = new Date();
			$scope.isFlashSaleStart = false;
			$scope.isFlashSaleEnd = false;
			var startDayMoment = moment(startDay);
			$scope.formattedStartDay = startDayMoment.format(
				"YYYY/MM/DD HH:mm:ss"
			);

			var endDayMoment = moment(endDay);
			console.log($scope.flashSaleActive.data.endDay)
			$scope.formattedEndDay = endDayMoment.format("YYYY/MM/DD HH:mm:ss");
		})
		.catch(function(err) {
			console.log(err);
		});
	$('.owl-carousel').owlCarousel({
		loop: true,
		margin: 30,
		dots: true,
		nav: false,
		responsiveClass: true,
		responsive: {
			0: {
				items: 2,
				margin: 10,
				stagePadding: 20,
			},
			600: {
				items: 3,
				margin: 20,
				stagePadding: 50,
			},
			1000: {
				items: 4
			}
		}
	});


});

app.filter('vnNumber', function() {
	return function(input) {
		if (isNaN(input)) return input;

		var parts = input.toString().split('.');
		parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',');

		var formattedNumber = parts.join('.') + ' ₫';
		return formattedNumber;
	};
});