app.controller(
  "cartCtrl",
  function ($scope, $rootScope, ApiService, $timeout, $location) {
    console.log("cartCtrl");
    $scope.checkAll = false;
    $scope.listVoucher = [];
    $rootScope.discountPrice = 0;

    $timeout(function () {
      angular.forEach($rootScope.userLogin.carts, function (cart) {
        cart.checked = false;
      });
      $rootScope.getCheckedProducts = $rootScope.userLogin.carts.filter(
        function (c) {
          return c.checked;
        }
      );
      $scope.listCartAvailable = [];
      $rootScope.userLogin.carts.filter(function (c) {
        angular.forEach(c.productDTO.productDetails, function (c2) {
          if (c2.id == c.productDetailId) {
            if (c2.quantityInStock > 0) {
              console.log(c);
              $scope.listCartAvailable.push(c);
              return c;
            }
          }
        });
      });
      console.log($scope.listCartAvailable);
      $rootScope.userLogin.carts = $scope.listCartAvailable;
    }, 400);

    ApiService.callApi("GET", "/rest/voucher")
      .then(function (response) {
        $scope.listVoucher = response;
      })
      .catch(function (error) {
        console.log(error);
      });

    var selectedValue = 0;
    $scope.applyVoucher = function () {
      selectedValue = $("#voucherDiscount").val();
      $rootScope.discountPrice = selectedValue;
    };

    $scope.checkChooseVoucher = function () {
      console.log($rootScope.discountPrice, $("#voucherDiscount").val());
      if ($rootScope.discountPrice == 0 && $("#voucherDiscount").val() != "") {
        Swal.fire({
          title: "Thông báo",
          text: "Bạn đã chọn voucher nhưng chưa áp dụng. Bạn có muốn tiếp tục thanh toán không voucher giảm giá không?",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "OK",
        }).then((result) => {
          if (result.isConfirmed) {
            $timeout(function () {
              $location.path("/checkout");
            }, 0);
          }
        });
      } else {
        $location.path("/checkout");
      }
    };

    $scope.handleInputBlur = function (cart) {
      console.log(cart.quantityInCart);
      if (!cart.quantityInCart.trim() || cart.quantityInCart == 0) {
        cart.quantityInCart = 1;
      }
      $scope.updateCart(cart);
    };

    $scope.updateTotalAmount = function (cart, qty) {
      var numberPattern = /^[0-9]*$/;
      console.log(cart);
      if (!numberPattern.test(cart.quantityInCart)) {
        cart.quantityInCart = 1;
      }

      if (cart.quantityInCart < 0) {
        cart.quantityInCart = 1;
      }

      if (cart.quantityInCart > qty) {
        cart.quantityInCart = qty;
      }

      cart.totalAmount = totalAmount(cart);
      $scope.updateCart(cart);
    };

    $scope.increaseQuantity = function (cart, qty) {
      console.log(cart);
      if (cart.quantityInCart < qty) {
        cart.quantityInCart++;
        cart.totalAmount = totalAmount(cart);
        $scope.updateCart(cart);
      }
    };

    $scope.decreaseQuantity = function (cart) {
      console.log(cart);
      if (cart.quantityInCart > 1) {
        cart.quantityInCart--;
        cart.totalAmount = totalAmount(cart);
        $scope.updateCart(cart);
      }
    };

    function totalAmount(cart) {
      var price =
        $rootScope.getLatestPrice(cart.productDTO.productDetails[0].prices)
          .price -
        $rootScope.getLatestPrice(cart.productDTO.productDetails[0].prices)
          .price *
          cart.productDTO.discountPrice;
      return (price * cart.quantityInCart).toFixed(0);
    }

    $scope.updateCart = function (cart) {
      console.log(cart);
      ApiService.callApi(
        "PUT",
        "/rest/update-cart/" + $rootScope.userLogin.accountId,
        cart
      )
        .then(function (response) {
          console.log(response);
        })
        .catch(function (error) {
          console.log(error);
        });
    };

    $scope.checkAll = false;

    $rootScope.getCheckedProducts = [];

    $scope.toggleAllCheckboxes = function () {
    //   cart.productDTO.productDetails.find(function (pdt) {
    //     return pdt.id == cart.productDetailId;
    //   });
	  angular.forEach($rootScope.userLogin.carts, function (cart) {
        cart.checked = true;
      });
      $rootScope.getCheckedProducts = $rootScope.userLogin.carts.filter(
        function (c) {
          return c.checked;
        }
      );
    };

    $scope.updateCheckAll = function () {
      $scope.checkAll = $rootScope.userLogin.carts.every(function (cart) {
        return cart.checked;
      });

      $rootScope.getCheckedProducts = $rootScope.userLogin.carts.filter(
        function (c) {
          return c.checked;
        }
      );
      console.log($rootScope.getCheckedProducts);
    };

    $timeout(function () {
      try {
        $rootScope.getTotalPrice = function () {
          var totalPrice = 0;
          angular.forEach($rootScope.userLogin.carts, function (cart) {
            if (cart.checked) {
              totalPrice += $scope.calculateProductTotal(cart);
            }
          });
          return totalPrice.toFixed(0);
        };
      } catch (error) {
        console.log(error);
      }
    }, 1000);

    $scope.calculateProductTotal = function (cart) {
      var productTotal = 0;

      var productDetails = cart.productDTO.productDetails.find(function (pdt) {
        return pdt.id == cart.productDetailId;
      });

      if (productDetails) {
        var price = 0;
        if (cart.productDTO.sale) {
          price =
            productDetails.prices.reduce(function (max, curr) {
              return new Date(max.createdDate) > new Date(curr.createdDate)
                ? max
                : curr;
            }).price *
            (1 - cart.productDTO.discountPrice);
        } else {
          price = productDetails.prices[0].price;
        }

        productTotal = price * cart.quantityInCart;
      }

      return productTotal;
    };
  }
);
