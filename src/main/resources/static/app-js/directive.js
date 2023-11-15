
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
  
  app.directive("niceSelect", function () {
    return {
      restrict: "A",
      link: function (scope, element) {
        $(element).niceSelect();
      },
    };
  });
  