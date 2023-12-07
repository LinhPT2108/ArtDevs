app.controller(
  "wishlistCtrl",
  function ($scope, $rootScope, ApiService, $timeout) {
    $scope.typeS = ["-price", "price", "productName", "productName"];
    console.log($rootScope.userLogin);
    $timeout(function () {
      ApiService.callApi(
        "GET",
        "/rest/wishlist-site/" + $rootScope.userLogin.accountId
      )
        .then(function (response) {
          console.log(response);
          $scope.listProductsAll = response;
        })
        .catch(function (err) {
          console.log(err);
        });
    }, 500);
  }
);
