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
