app.directive("customHeader", function () {
  return {
    restrict: "E",
    templateUrl: "../templates/components/_header.html",
  };
});

app.directive("customFooter", function () {
  return {
    restrict: "E",
    templateUrl: "../templates/components/_footer.html",
  };
});

app.directive("customBanner", function () {
  return {
    restrict: "E",
    templateUrl: "../templates/components/banner.html",
    controller: function ($scope, $timeout, ApiService) {
      ApiService.callApi("GET", "/rest/banner")
        .then(function (resp) {
          $scope.listBanner = resp;
          $timeout(function () {
            $scope.slickBanner();
          }, 1000);
        })
        .catch(function (err) {
          console.log(err);
        });

      $scope.slickBanner = function () {
        $(".home-slider-4").owlCarousel({
          items: 1,
          autoplay: true,
          autoplayTimeout: 5000,
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
        });
      };
    },
  };
});
app.directive("customFlashsale", function ($timeout, ApiService) {
  return {
    restrict: "E",
    templateUrl: "../templates/components/slick-flash-sale.html",
    link: function (scope, element) {
      console.log("listCarousel");
      ApiService.callApi("GET", "/rest/flash-sale-active")
        .then(function (resp) {
          scope.flashSaleActive = resp;
          console.log(resp);
          scope.flashSaleActive.endDay = new Date(scope.flashSaleActive.endDay);
          scope.flashSaleActive.startDay = new Date(
            scope.flashSaleActive.startDay
          );
          var startDay = scope.flashSaleActive.startDay;
          var endDay = scope.flashSaleActive.endDay;
          scope.nowDay = new Date();
          scope.isFlashSaleStart = false;
          scope.isFlashSaleEnd = false;
          var startDayMoment = moment(startDay);
          scope.formattedStartDay = startDayMoment.format(
            "YYYY/MM/DD HH:mm:ss"
          );

          var endDayMoment = moment(endDay);
          scope.formattedEndDay = endDayMoment.format("YYYY/MM/DD HH:mm:ss");
          $timeout(function () {
            scope.slickFlashsale();
            scope.countdown();
          }, 500);
        })
        .catch(function (err) {
          console.log(err);
        });

      scope.slickFlashsale = function () {
        element.find(".listCarousel").owlCarousel({
          items: 4,
          autoplay: true,
          autoplayTimeout: 5000,
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
      scope.countdown = function () {
        console.log("countdown");
        $("[data-countdown]").each(function () {
          var $this = $(this),
            finalDate = $(this).data("countdown");
          $this.countdown(finalDate, function (event) {
            $this.html(event.strftime("%D ngày %H giờ %M phút %S giây"));
          });
        });
      };

      scope.getProgressWidth = function (quantitySold, discountedQuantity) {
        var percentSold = (quantitySold / discountedQuantity) * 100;
        return percentSold + "%";
      };
    },
  };
});
app.directive("niceSelect", function ($timeout) {
  return {
    restrict: "A",
    link: function (scope, element) {
		$timeout(function(){
			
			$(element).niceSelect();
		},100)
    },
  };
});

app.directive("numbersOnly", function () {
  return {
    require: "ngModel",
    link: function (scope, element, attr, ngModelCtrl) {
      function fromUser(text) {
        console.log(text);
        if (text) {
          var transformedInput = text.replace(/[^0-9]/g, "");
          if (transformedInput !== text) {
            ngModelCtrl.$setViewValue(transformedInput);
            ngModelCtrl.$render();
          }
          return transformedInput;
        }
        return undefined;
      }
      ngModelCtrl.$parsers.push(fromUser);
    },
  };
});

app.directive("quantityControl", function ($rootScope) {
  return {
    restrict: "A",
    link: function (scope, element) {
      $rootScope.qty = 1;
      var input = element.find(".input-number");
      var updateValue = function (newValue) {
        var min = parseInt(input.attr("data-min")) || 0;
        var max = parseInt(input.attr("data-max"));

        if (newValue < min) {
          newValue = min;
        } else if (newValue > max) {
          newValue = max;
        }

        input.val(newValue);
      };

      element.on("click", ".btn-number", function () {
        var type = $(this).data("type");
        var currentValue = parseInt(input.val()) || 0;
        var step = 1;
        if (type === "minus") {
          updateValue(currentValue - step);
        } else if (type === "plus") {
          updateValue(currentValue + step);
        }
        $rootScope.qty = parseInt(input.val());
        console.log($rootScope.qty);
      });

      input.on("input", function () {
        var currentValue = parseInt(input.val()) || 0;
        var min = parseInt(input.attr("data-min")) || 0;
        var max = parseInt(input.attr("data-max"));

        if (currentValue < min) {
          updateValue(min);
        } else if (currentValue > max) {
          updateValue(max);
        }

        $rootScope.qty = parseInt(input.val());
        console.log($rootScope.qty);
      });
    },
  };
});

app.directive("stopPropagation", function (ApiService, $timeout, $rootScope) {
  return {
    restrict: "A",
    link: function (scope, element) {
      scope.removeActiveClassFromLinks = function () {
        var anchorElements = document.querySelectorAll(".nav-link-select");
        anchorElements.forEach(function (a) {
          a.classList.remove("active");
        });
      };

      scope.setActiveClass = function (element) {
        element.classList.add("active");
      };

      element.bind("click", function (e) {
        //add class and remove class "active"
        scope.removeActiveClassFromLinks();
        scope.setActiveClass(e.currentTarget);

        //get api list product by category id
        var hrefValue = e.currentTarget.getAttribute("href");
        $(".btnViewAll").attr("href", "/products?c=" + hrefValue);
        console.log("Href value:", hrefValue);
        ApiService.callApi("GET", "/rest/product-by-category/" + hrefValue)
          .then(function (resp) {
            console.log(resp);
            $rootScope.listProductsbyCategory = resp;
          })
          .catch(function (err) {
            console.log(err);
          });
        return false;
      });
    },
  };
});

app.directive("quickViewModal", function (ApiService, $rootScope, $timeout) {
  return {
    restrict: "A",
    link: function (scope, element) {
      element.on("click", function () {
        var productId = element.attr("data-product-id");
        $(".input-number").val(1);
        ApiService.callApi("GET", "/rest/product/" + productId)
          .then(function (resp) {
            $rootScope.qvpd = resp;
            console.log($rootScope.qvpd);
          })
          .catch(function (err) {
            console.log(err);
          });

        scope.clickFirstElement = function () {
          var firstElement = document.querySelectorAll(".btn-type");
          if (firstElement) {
            angular.element(firstElement).triggerHandler("click");
          }
        };
        $timeout(function () {
          scope.clickFirstElement();
        }, 400);
      });
    },
  };
});

app.directive("onFinishRender", function ($timeout) {
  return {
    restrict: "A",
    link: function (scope, element, attr) {
      if (scope.$last === true) {
        $timeout(function () {
          scope.$emit("ngRepeatFinished");
        });
      }
    },
  };
});

app.directive("owlCarousel", function () {
  return {
    restrict: "A",
    link: function (scope, element) {
      scope.$on("ngRepeatFinished", function () {
        scope.slider = function () {
          element.owlCarousel({
            items: 1,
            autoplay: true,
            autoplayTimeout: 5000,
            smartSpeed: 400,
            autoplayHoverPause: true,
            nav: true,
            loop: true,
            merge: true,
            dots: false,
            navText: [
              '<i class=" ti-arrow-left"></i>',
              '<i class=" ti-arrow-right"></i>',
            ],
          });
        };
        scope.slider();
      });
    },
  };
});

app.directive("modalHidden", function ($rootScope) {
  return {
    restrict: "A",
    link: function (scope, element) {
      element.on("hidden.bs.modal", function (e) {
        $rootScope.qvpd = null;
        $rootScope.choiceProductDetailId = null;
        $(".quickview-slider-active")
          .trigger("destroy.owl.carousel")
          .removeClass("owl-carousel owl-loaded");
        $(".quickview-slider-active")
          .find(".owl-stage-outer")
          .children()
          .unwrap();
      });
    },
  };
});

app.directive("logoutCtrl", function ($rootScope, ApiService, $location) {
  return {
    restrict: "A",
    link: function (scope, element) {
      element.on("click", function () {
        console.log("logoutCtrl");
        ApiService.callApi("GET", "/account/logout")
          .then(function (response) {
            console.log(true);
            $rootScope.userLogin = null;
            console.log($rootScope.userLogin);
            window.location.href = "/account/login";
          })
          .catch(function (error) {
            console.log(error);
          });
      });
    },
  };
});

app.directive("chooseImage", function () {
  return {
    restrict: "A",
    link: function (scope, element) {
      element.bind("click", function () {
        var fileInput = document.getElementById("image");
        var imageContainer = document.getElementById("imageContainer");

        fileInput.addEventListener("change", function () {
          var selectedFile = fileInput.files[0];
          var maxSize = 1 * 1024 * 1024;
          var allowedFormats = [".jpg", ".jpeg", ".png"];

          if (selectedFile) {
            var fileSize = selectedFile.size;
            var fileFormat = selectedFile.name
              .substring(selectedFile.name.lastIndexOf("."))
              .toLowerCase();

            if (fileSize <= maxSize && allowedFormats.includes(fileFormat)) {
              var reader = new FileReader();

              reader.onload = function (e) {
                imageContainer.style.backgroundImage =
                  "url(" + e.target.result + ")";
                imageContainer.style.display = "block";
              };

              reader.readAsDataURL(selectedFile);
            } else {
              Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Dung lượng file tối đa 1 MB và có định dạng: .JPEG, .PNG, .JPG!",
              });
              fileInput.value = null;
            }
          }
        });

        fileInput.click();
      });
    },
  };
});

app.directive(
  "addToCart",
  function ($rootScope, ApiService, $location, $timeout) {
    return {
      restrict: "A",
      scope: {
        product: "=",
        quantity: "=",
        pdDetailId: "=",
      },
      link: function (scope, element, attrs) {
        element.bind("click", function (event) {
          event.preventDefault();

          let userLogin = $rootScope.userLogin;
          console.log(userLogin);
          console.log(scope.quantity);
          console.log(scope.pdDetailId);
          console.log($rootScope.choiceProductDetailId);
          if ($rootScope.choiceProductDetailId != null) {
            scope.pdDetailId = $rootScope.choiceProductDetailId;
          }
          console.log(scope.pdDetailId);
          if (userLogin == null) {
            Swal.fire({
              title: "Vui lòng đăng nhập !!",
              text: "Cần đăng nhập để sử dụng chức năng này!",
              icon: "warning",
              showCancelButton: true,
              confirmButtonColor: "#3085d6",
              cancelButtonColor: "#d33",
              confirmButtonText: "Đăng nhập!",
              cancelButtonText: "Trở về",
            }).then((result) => {
              if (result.isConfirmed) {
                $("[data-dismiss=modal]").trigger({ type: "click" });
                $timeout(function () {
                  $location.path("/account/login");
                }, 100);
              }
            });
          } else {
            console.log(scope.product);
            ApiService.callApi(
              "POST",
              "/rest/cart/" + $rootScope.userLogin.accountId,
              {
                productDetailId: scope.pdDetailId,
                productDTO: scope.product,
                quantityInCart: scope.quantity,
              }
            )
              .then(function (resp) {
                console.log(resp);
                if (resp != 400) {
                  const Toast = Swal.mixin({
                    toast: true,
                    position: "top-end",
                    showConfirmButton: false,
                    timer: 1500,
                    timerProgressBar: true,
                    didOpen: (toast) => {
                      toast.onmouseenter = Swal.stopTimer;
                      toast.onmouseleave = Swal.resumeTimer;
                    },
                  });
                  Toast.fire({
                    icon: "success",
                    title: "Thêm vào giỏ hàng thành công !",
                  });
                  var index = $rootScope.userLogin.carts.findIndex(
                    (a) => a.cartId === resp.cartId
                  );

                  if (index !== -1) {
                    $rootScope.userLogin.carts[index] = resp;
                  } else {
                    $rootScope.userLogin.carts.push(resp);
                  }
                }
              })
              .catch(function (err) {
                console.log(err);
              });
          }
        });
      },
    };
  }
);