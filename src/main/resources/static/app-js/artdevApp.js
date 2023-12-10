var app = angular.module("artdevApp", [
  "ngRoute",
  "angularUtils.directives.dirPagination",
]);

const api = "http://localhost:8080";
app.config(function ($routeProvider, $locationProvider) {
  $routeProvider
    .when("/", {
      redirectTo: "/ArtDevs",
    })
    .when("/ArtDevs", {
      templateUrl: "templates/user/views/main.html",
      controller: "mainCtrl",
    })
    .when("/account/profile", {
      templateUrl: "templates/user/views/account.html",
      controller: "profileCtrl",
    })
    .when("/account/address", {
      templateUrl: "templates/user/views/account.html",
      controller: "addressCtrl",
    })
    .when("/contact", {
      templateUrl: "templates/user/views/contact.html",
    })
    .when("/account/change-password", {
      templateUrl: "templates/user/views/account.html",
      controller: "changePasswordCtrl",
    })
    .when("/account/wishlist", {
      templateUrl: "templates/user/views/account.html",
      controller: "wishlistCtrl",
    })
    .when("/account/purchase-order/:type", {
      templateUrl: "templates/user/views/account.html",
      controller: "orderCtrl",
    })
    .when("/account/forgot-password", {
      templateUrl: "templates/user/views/forgotPass.html",
      controller: "forgotPasswordCtrl",
    })
    .when("/account/login", {
      templateUrl: "templates/user/views/login.html",
      controller: "loginCtrl",
    })
    .when("/account/register", {
      templateUrl: "templates/user/views/register.html",
      controller: "registerCtrl",
    })
    .when("/account/verify-code/:accountId", {
      templateUrl: "templates/user/views/verify-code.html",
      controller: "verifyCtrl",
    })
    .when("/cart", {
      templateUrl: "templates/user/views/cart.html",
      controller: "cartCtrl",
    })
    .when("/checkout", {
      templateUrl: "templates/user/views/checkout.html",
      controller: "checkoutCtrl",
    })
    .when("/products/:productId", {
			templateUrl: "templates/user/views/product-detail.html",
			controller: "productdetailCtrl",
		})
    .when("/products", {
      templateUrl: "templates/user/views/shop-grid.html",
      controller: "productsiteCtrl",
    })
    .when("/about-us", {
      templateUrl: "templates/user/views/about-us.html",
    })
    .otherwise({
      redirectTo: "/ArtDevs",
    });

  $locationProvider.html5Mode(true);
});

app.service("ApiService", function ($http) {
  this.callApi = function (method, url, data, params) {
    return $http({
      method: method,
      url: api + url,
      data: data,
      params: params,
    })
      .then(function successCallback(response) {
        return response.data;
      })
      .catch(function errorCallback(response) {
        console.log(response);
        throw new Error(response.statusText);
      });
  };
});

app.service("DataService", function ($http) {
  this.getCategories = function () {
    return $http.get(api + "/rest/category");
  };

  this.getBrands = function () {
    return $http.get(api + "/rest/manufacturer");
  };
});

app.run(function ($rootScope, DataService, ApiService, $timeout) {
  DataService.getCategories().then(function (response) {
    $rootScope.categories = response.data;
    $timeout(function () {
      var firstAnchor = document.querySelectorAll(".nav-link-select");
      if (firstAnchor) {
        angular.element(firstAnchor).triggerHandler("click");
      }
    }, 500);
  });

  DataService.getBrands().then(function (response) {
    $rootScope.brands = response.data;
  });

  $rootScope.getLatestPrice = function (prices) {
    if (!prices || prices.length === 0) {
      return null;
    }

    prices.sort(function (a, b) {
      return new Date(b.createdDate) - new Date(a.createdDate);
    });

    return prices[0];
  };

  ApiService.callApi("GET", "/rest/userLogin")
    .then(function (response) {
      $rootScope.userLogin = response == "" ? null : response;
      console.log($rootScope.userLogin);
      if ($rootScope.userLogin && $rootScope.userLogin.carts) {
        $rootScope.userLogin.carts = $rootScope.userLogin.carts.filter(
          function (cart) {
            return cart.productDTO.available === true;
          }
        );

        ApiService.callApi(
          "GET",
          "/rest/wishlist/" + $rootScope.userLogin.accountId
        )
          .then(function (response) {
            console.log(response);
            $rootScope.wishlistAccount = response;
          })
          .catch(function (err) {
            console.log(err);
          });
      }
    })
    .catch(function (error) {
      console.log("Lỗi" + error);
    });

  $rootScope.checkInWishlist = function (productId) {
    if ($rootScope.wishlistAccount != undefined) {
      return $rootScope.wishlistAccount.filter(function (w) {
        return w.product.productId == productId ? true : false;
      });
    }
  };
});

app.controller(
  "headerCtrl",
  function ($scope, DataService, $location, $rootScope, $timeout, ApiService) {
    console.log($scope.selectedCategory);
    $scope.submitSearch = function () {
      $location.path("/products").search({
        c: $scope.selectedCategory,
        keyword: $scope.keyword,
      });
      console.log($scope.selectedCategory);
      console.log($scope.keyword);
    };

    $scope.isActive = function (...viewLocations) {
      return viewLocations.some((location) =>
        $location.path().includes(location)
      );
    };

    $(".top-search a").on("click", function (event) {
      event.preventDefault();
      $(".search-top").toggleClass("active");
    });

    $scope.calculateTotalAmount = function () {
      $scope.totalAmount = 0;
      if ($scope.userLogin != null) {
        $timeout(function () {
          for (var i = 0; i < $scope.userLogin.carts.length; i++) {
            var cartItem = $scope.userLogin.carts[i];
            if (cartItem.productDTO.available) {
              var price = 0;

              if (cartItem.productDTO.sale) {
                price =
                  $rootScope.getLatestPrice(
                    cartItem.productDTO.productDetails[0].prices
                  ).price -
                  $rootScope.getLatestPrice(
                    cartItem.productDTO.productDetails[0].prices
                  ).price *
                    cartItem.productDTO.discountPrice;
              } else {
                price = $rootScope.getLatestPrice(
                  cartItem.productDTO.productDetails[0].prices
                ).price;
              }
              $scope.totalAmount += price * cartItem.quantityInCart;
            }
          }
        }, 300);
      }
    };
    $timeout(function () {
      $scope.$watch(
        "userLogin.carts",
        function () {
          $scope.calculateTotalAmount();
        },
        true
      );
    }, 300);

    $rootScope.removeToCart = function (event, cartId, index) {
      event.preventDefault();
      console.log(cartId + "index: " + index);
      ApiService.callApi("DELETE", "/rest/cart/" + cartId)
        .then(function (resp) {
          console.log(resp);
          if (resp == 200) {
            $scope.userLogin.carts.splice(index, 1);
            const Toast = Swal.mixin({
              toast: true,
              position: "top-end",
              showConfirmButton: false,
              timer: 2000,
              timerProgressBar: true,
              didOpen: (toast) => {
                toast.onmouseenter = Swal.stopTimer;
                toast.onmouseleave = Swal.resumeTimer;
              },
            });
            Toast.fire({
              icon: "success",
              title: "Xóa khỏi giỏ hàng thành công",
            });
          } else {
            const Toast = Swal.mixin({
              toast: true,
              position: "top-end",
              showConfirmButton: false,
              timer: 3000,
              timerProgressBar: true,
              didOpen: (toast) => {
                toast.onmouseenter = Swal.stopTimer;
                toast.onmouseleave = Swal.resumeTimer;
              },
            });
            Toast.fire({
              icon: "warning",
              title: "Có lỗi xảy ra, vui lòng thử lại !",
            });
          }
        })
        .catch(function (error) {
          console.log(error);
          Swal.fire({
            icon: "warning",
            title: "Có lỗi xảy ra",
            text: "Vui  lòng thử lại !",
            showConfirmButton: true,
          });
        });
    };
  }
);

app.controller("mainCtrl", function ($scope, $timeout, $rootScope, ApiService) {
  $scope.quantity = 1;
  console.log("mainCtrl");
  $timeout(function () {
    var firstAnchor = document.querySelectorAll(".nav-link-select");
    if (firstAnchor) {
      angular.element(firstAnchor).triggerHandler("click");
    }
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
  };
  $scope.limit = 12;
  ApiService.callApi("GET", "/rest/product-today")
    .then(function (resp) {
      $scope.listProductsToday = resp;
      console.log($scope.listProductsToday);
    })
    .catch(function (err) {
      console.log(err);
    });

  $scope.showMore = function () {
    $scope.limit += 12;
  };
});
