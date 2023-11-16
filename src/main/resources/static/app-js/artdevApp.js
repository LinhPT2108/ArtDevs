var app = angular.module("artdevApp", ["ngRoute"]);
app.config(function ($routeProvider, $locationProvider) {
  $routeProvider
    .when("/", {
      templateUrl: "templates/user/views/main.html",
    })
    .when("/ArtDevs", {
      templateUrl: "templates/user/views/main.html",
    })
    .when("/account/profile", {
      templateUrl: "templates/user/views/account.html",
      controller: "profileCtrl",
    })
    .when("/account/address", {
      templateUrl: "templates/user/views/account.html",
      controller: "addressCtrl",
    })
    .when("/account/change-password", {
      templateUrl: "templates/user/views/account.html",
      controller: "changePasswordCtrl",
    })
    .when("/account/forgot-password", {
      templateUrl: "templates/user/views/forgotPass.html",
      controller: "forgotPasswordCtrl",
    })
    .when("/account/login", {
      templateUrl: "templates/user/views/login.html",
      controller: "loginCtrl"
    })
    .when("/account/register", {
      templateUrl: "templates/user/views/register.html",
      controller: "registerCtrl"
    })
    .when("/cart", {
      templateUrl: "templates/user/views/cart.html",
      controller: "cartCtrl",
    })
    .when("/product", {
      templateUrl: "templates/user/views/shop-grid.html",
      controller: "productsiteCtrl",
    })
    .otherwise({
      redirectTo: "/",
    });

  $locationProvider.html5Mode(true);
});

app.service("ApiService", function ($http) {
  this.callApi = function (method, url, data) {
    return $http({
      method: method,
      url: url,
      data: data,
    })
      .then(function successCallback(response) {
        return response.data;
      })
      .catch(function errorCallback(response) {
        throw new Error(response.statusText);
      });
  };
});

app.controller("headerCtrl", function ($scope) {
  $(".top-search a").on("click", function () {
    $(".search-top").toggleClass("active");
  });
});

app.controller("mainCtrl", function ($scope, $timeout) {
  console.log(123);
  $scope.quickViews = function () {
    $(".quickview-slider-active").owlCarousel({
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

  $timeout(function () {
    $scope.quickViews();
  });
});
