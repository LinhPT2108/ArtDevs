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
    controller: function ($scope, $timeout) {
      $scope.listBanner = ["banner1", "banner2", "banner3", "banner4"];
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
      });
    },
  };
});
app.directive("customFlashsale", function ($timeout) {
  return {
    restrict: "E",
    templateUrl: "../templates/components/slick-flash-sale.html",
    link: function (scope, element) {
      console.log("listCarousel");
      scope.slickFlashsale = function () {
        element.find(".listCarousel").owlCarousel({
          items: 6,
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
            1440: {
              items: 5,
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
      $timeout(function () {
        scope.slickFlashsale();
        scope.countdown();
      });
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

app.directive("quantityControl", function () {
  return {
    restrict: "A",
    link: function (scope, element) {
      // Lấy input element trong directive
      var input = element.find(".input-number");

      var updateValue = function (newValue) {
        var min = parseInt(input.attr("data-min")) || 0;

        if (newValue < min) {
          newValue = min;
        }

        input.val(newValue);
      };

      element.on("click", ".btn-number", function () {
        var type = $(this).data("type");
        var currentValue = parseInt(input.val()) || 0;
        var step = 1;
        console.log(type);
        if (type === "minus") {
          updateValue(currentValue - step);
        } else if (type === "plus") {
          updateValue(currentValue + step);
        }
      });

      input.on("input", function () {
        var currentValue = parseInt(input.val()) || 0;
        var min = parseInt(input.attr("data-min")) || 0;

        if (currentValue < min) {
          updateValue(min);
        }
      });
    },
  };
});

app.directive("stopPropagation", function (ApiService, $timeout, $rootScope) {
  return {
    restrict: "A",
    link: function (scope, element) {
      scope.showLoading = function(){
        
      }
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
        console.log("Href value:", hrefValue);
        ApiService.callApi("GET", "/rest/category/" + hrefValue)
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
