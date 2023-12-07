app.controller(
  "wishlistCtrl",
  function ($scope, $rootScope, ApiService, $timeout) {
    $scope.typeS = ["-price", "price", "productName", "productName"];
    $scope.currentPage = 1;
    $scope.pageSize = 12;
    console.log($rootScope.userLogin);
    $timeout(function () {
      ApiService.callApi(
        "GET",
        "/rest/wishlist-site/" + $rootScope.userLogin.accountId
      )
        .then(function (response) {
          $scope.listProductsAll = response;
          console.log($scope.listProductsAll);
        })
        .catch(function (err) {
          console.log(err);
        });
    }, 500);
    $scope.choiceProduct = function (
      productDetailId,
      $event,
      discountPrice,
      isSale
    ) {
      console.log(isSale, discountPrice);
      $scope.isSale = isSale;
      $scope.discountPrice = discountPrice;
      $scope.quantity = 1;
      var elements = document.querySelectorAll(".btn-type");
      elements.forEach(function (element) {
        element.classList.remove("type-active");
      });

      var targetElement = $event.target;
      targetElement.classList.add("type-active");

      ApiService.callApi("GET", "/rest/product-detail/" + productDetailId)
        .then(function (resp) {
          $scope.pdt = resp;
          $rootScope.choiceProductDetailId = productDetailId;
          console.log($scope.pdt);
          console.log($rootScope.choiceProductDetailId);
        })
        .catch(function (err) {
          console.log(err);
        });
      $(".removeLike").onclick(function () {
        console.log(123);
      });
    };

    $scope.removeLike = function (pdId) {
      //   $scope.listProductsAll = $rootScope.wishlistAccount;
      let index2 =  $scope.listProductsAll.findIndex(
        (wl) => wl.productId == pdId
      );
      $scope.listProductsAll.splice(index2, 1);
    };
  }
);
