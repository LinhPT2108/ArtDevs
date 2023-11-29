var app = angular.module("artdevApp", ["ngRoute"]);

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
      controller: "loginCtrl",
    })
    .when("/account/register", {
      templateUrl: "templates/user/views/register.html",
      controller: "registerCtrl",
    })
    .when("/cart", {
      templateUrl: "templates/user/views/cart.html",
      controller: "cartCtrl",
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
  this.callApi = function (method, url, data) {
    return $http({
      method: method,
      url: api + url,
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

// Service trong AngularJS để gọi API từ backend
app.service("DataService", function ($http) {
  this.getCategories = function () {
    return $http.get(api + "/rest/category");
  };

  this.getBrands = function () {
    return $http.get(api + "/rest/manufacturer");
  };
});

app.run(function ($rootScope, DataService, ApiService) {
  DataService.getCategories().then(function (response) {
    $rootScope.categories = response.data;
    console.log($rootScope.categories);
  });

  DataService.getBrands().then(function (response) {
    $rootScope.brands = response.data;
    console.log($rootScope.brands);
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
      console.log(response);
    })
    .catch(function (error) {
      console.log(error);
    });
});

app.controller("headerCtrl", function ($scope, DataService, $location) {
  $scope.isActive = function (...viewLocations) {
    return viewLocations.some((location) =>
      $location.path().includes(location)
    );
  };

  $(".top-search a").on("click", function (event) {
    event.preventDefault();
    $(".search-top").toggleClass("active");
  });
});

app.controller("mainCtrl", function ($scope, $timeout, $rootScope, ApiService) {
  $scope.quantity = 1;
  console.log("mainCtrl");

  $timeout(function () {
    var firstAnchor = document.querySelectorAll(".nav-link-select");
    if (firstAnchor) {
      angular.element(firstAnchor).triggerHandler("click");
    }
  }, 100);

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
        console.log($scope.pdt);
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
  // Service trong AngularJS
app.service('AuthService', function($http) {
    this.login = function(credentials) {
        return $http.post('/api/login', credentials);
    };
});

// Controller trong AngularJS
app.controller('LoginController', function($scope, AuthService) {
    $scope.login = function(credentials) {
        AuthService.login(credentials).then(function(response) {
            // Xử lý sau khi đăng nhập thành công
            // Chuyển hướng đến "/ArtDevs"
            window.location.href = '/ArtDevs';
        }).catch(function(error) {
            // Xử lý khi có lỗi đăng nhập
            console.error('Error logging in:', error);
        });
    };
});

});
