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

      $timeout(function () {
        $scope.slickBanner();
      }, 500);
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
          scope.flashSaleActive.endDay = new Date(scope.flashSaleActive.endDay);

          var endDayMoment = moment(scope.flashSaleActive.endDay);
          scope.formattedEndDay = endDayMoment.format("YYYY/MM/DD HH:mm:ss");

          console.log(scope.flashSaleActive);
          console.log(scope.formattedEndDay);
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

      $timeout(function () {
        scope.slickFlashsale();
        scope.countdown();
      }, 500);
    },
  };
});
app.directive("niceSelect", function ($timeout) {
  return {
    restrict: "A",
    link: function (scope, element) {
      $(element).niceSelect();
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
      });
    },
  };
});

app.directive("stopPropagation", function (ApiService, $timeout, $rootScope) {
  return {
    restrict: "A",
    link: function (scope, element) {
      scope.showLoading = function () {};
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
        $(".btnViewAll").attr("href", "/product/category/" + hrefValue);
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
