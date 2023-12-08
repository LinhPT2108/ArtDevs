var host = "http://localhost:8080";
app.controller(
  "productdetailCtrl",
  function ($scope, $http, $timeout, $routeParams, ApiService, $rootScope) {
    console.log($rootScope.flashSaleActive);
    var productId = $routeParams.productId;
    $scope.currentPage = 1;
    $scope.product = {};
    $scope.star = [];
    $scope.formattedEndDay = null;
    $scope.listProductByCategory = [];
    $scope.clickFirstElement = function () {
      var firstElement = document.querySelectorAll(".btn-type");
      if (firstElement) {
        console.log("click");
        angular.element(firstElement).triggerHandler("click");
      }
    };
    $timeout(function () {
      $scope.clickFirstElement();
    }, 500);
    $http
      .get(host + "/rest/product/" + productId)
      .then(function (res) {
        $scope.product = res.data;
        console.log($scope.product);
        $scope.pddt = [];
        angular.forEach($scope.product.productDetails, function (value) {
          angular.forEach(value.comments, function (comment) {
            $scope.pddt.push(comment);
          });
        });
        console.log($scope.pddt);
        $scope.star = $scope.product.star;
        $http
          .get(
            host + "/rest/product-by-category/" + res.data.category.categoryId
          )
          .then(function (respone) {
            $scope.listProductByCategory = respone.data;
            $timeout(function () {
              $scope.slickFlashsale();
            }, 100);
          })
          .catch(function (err) {
            console.log(err);
          });
      })
      .catch(function (err) {
        console.log(err);
      });
    $http
      .get(host + "/rest/flash-sale-active")
      .then(function (resp) {
        $scope.flashSaleActive = resp;
        $scope.currentProduct =
          $scope.flashSaleActive.data.promotionalDetailsList.filter(function (
            i
          ) {
            return i.product.productId == productId;
          });
        console.log($scope.currentProduct);
      })
      .catch(function (err) {
        console.log(err);
      });
    $scope.slickFlashsale = function () {
      $(".listCarousel").owlCarousel({
        items: 3,
        autoplay: true,
        autoplayTimeout: 3000,
        smartSpeed: 400,
        autoplayHoverPause: true,
        nav: true,
        loop: true,
        merge: true,
        dots: true,
        navText: [
          '<i class=" ti-arrow-left"></i>',
          '<i class=" ti-arrow-right"></i>',
        ],
        responsive: {
          0: {
            items: 1,
          },
          300: {
            items: 1,
          },
          480: {
            items: 2,
          },
          768: {
            items: 3,
          },
          1170: {
            items: 4,
          },
        },
      });
    };

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
        })
        .catch(function (err) {
          console.log(err);
        });
    };
  }
);

app.filter("vnNumber", function () {
  return function (input) {
    if (isNaN(input)) return input;

    var parts = input.toString().split(".");
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");

    var formattedNumber = parts.join(".") + " ₫";
    return formattedNumber;
  };
});
