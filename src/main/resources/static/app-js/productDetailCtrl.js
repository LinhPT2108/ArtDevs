var host = "http://localhost:8080";
app.controller("productdetailCtrl", function($scope, $http, $timeout, $routeParams) {
	var productId = $routeParams.productId;
	$scope.product = {};
	$scope.star = [];
	$http.get(host + "/rest/product/" + productId)
		.then(function(res) {
			$scope.product = res.data;
			$scope.star = $scope.product.star;

		})
		.catch(function(err) {
			console.log(err);
		})
	
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