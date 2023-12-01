app
  .controller(
    "productsiteCtrl",
    function ($scope, ApiService, $rootScope, $filter, $location) {
      console.log("productsiteCtrl");
      var cValue = $location.search().c;
      var bValue = $location.search().b;
      var keyword = $location.search().keyword;
      console.log(cValue, bValue, keyword);

      $scope.currentPage = 1;
      $scope.pageSize = 12;
      $scope.typeS = ["-price", "price", "productName", "productName"];
      ApiService.callApi("GET", "/rest/product-all", null, {
        c: cValue,
        b: bValue,
        keyword: keyword,
      })
        .then(function (resp) {
          $scope.listProductsAll = resp;
        })
        .catch(function (err) {
          console.log(err);
        });

      $scope.choiceProduct = function (
        productDetailId,
        $event,
        discountPrice,
        isSale
      ) {
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
          })
          .catch(function (err) {
            console.log(err);
          });
      };

      $scope.sortList = function (tc) {
        switch (tc) {
          case "priceAsc":
            $scope.listProductsAll.sort((a, b) => {
              let priceA = a.sale
                ? a.productDetails[0].prices.reduce((max, curr) =>
                    new Date(max.createdDate) > new Date(curr.createdDate)
                      ? max
                      : curr
                  ).price *
                  (1 - a.discountPrice)
                : a.productDetails[0].prices[0].price;
              let priceB = b.sale
                ? b.productDetails[0].prices.reduce((max, curr) =>
                    new Date(max.createdDate) > new Date(curr.createdDate)
                      ? max
                      : curr
                  ).price *
                  (1 - b.discountPrice)
                : b.productDetails[0].prices[0].price;
              return priceA - priceB;
            });
            break;
          case "priceDesc":
            $scope.listProductsAll.sort((a, b) => {
              let priceA = a.sale
                ? a.productDetails[0].prices.reduce((max, curr) =>
                    new Date(max.createdDate) > new Date(curr.createdDate)
                      ? max
                      : curr
                  ).price *
                  (1 - a.discountPrice)
                : a.productDetails[0].prices[0].price;
              let priceB = b.sale
                ? b.productDetails[0].prices.reduce((max, curr) =>
                    new Date(max.createdDate) > new Date(curr.createdDate)
                      ? max
                      : curr
                  ).price *
                  (1 - b.discountPrice)
                : b.productDetails[0].prices[0].price;
              return priceB - priceA;
            });
            break;
          case "productName":
            $scope.listProductsAll.sort((a, b) => {
              return a.productName.localeCompare(b.productName);
            });
            break;
          case "-productName":
            $scope.listProductsAll.sort((a, b) => {
              return b.productName.localeCompare(a.productName);
            });
            break;
          default:
            break;
        }
      };
      $scope.search = {
        category: {},
        brand: {},
        minPrice: null,
        maxPrice: null,
      };

      $scope.applyPriceFilter = function () {
        $scope.search.minPrice = null;
        $scope.search.maxPrice = null;
      };
      $scope.$watch(
        "search",
        function (n, o) {
          var selected = [];
          for (var c in n.category) {
            if (n.category[c]) {
              selected.push(true);
            }
          }
          for (var b in n.brand) {
            if (n.brand[b]) {
              selected.push(true);
            }
          }
          // if (selected.length) {
          $scope.search.needFilter = true;
          // } else {
          //   $scope.search.needFilter = false;
          // }
        },
        true
      );

      $scope.checkMinPrice = function () {
        if (isNaN($scope.search.minPrice)) {
          $scope.search.minPrice = 1;
        } else if ($scope.search.minPrice < 0) {
          $scope.search.minPrice = 1;
        } else if ($scope.search.minPrice > $scope.search.maxPrice) {
          $scope.search.minPrice = $scope.search.maxPrice;
        }
      };

      $scope.checkMaxPrice = function () {
        if ($scope.search.minPrice == null) {
          $scope.search.minPrice = 1;
        }
        if (isNaN($scope.search.maxPrice)) {
          $scope.search.maxPrice = 1;
        } else if ($scope.search.maxPrice < 0) {
          $scope.search.maxPrice = 1;
        } else if ($scope.search.minPrice > $scope.search.maxPrice) {
          $scope.search.minPrice = $scope.search.maxPrice;
        }
      };

      $scope.isCategorySelected = false;
      $scope.isBrandSelected = false;

      $scope.checkCategorySelection = function () {
        $scope.isCategorySelected = Object.values($scope.search.category).some(
          (value) => value
        );
      };

      $scope.checkBrandSelection = function () {
        $scope.isBrandSelected = Object.values($scope.search.brand).some(
          (value) => value
        );
      };

      $scope.clearCategoryFilters = function () {
        angular.forEach($scope.search.category, function (value, key) {
          $scope.search.category[key] = false;
        });
        $scope.isCategorySelected = false;
      };

      $scope.clearBrandFilters = function () {
        angular.forEach($scope.search.brand, function (value, key) {
          $scope.search.brand[key] = false;
        });
        $scope.isBrandSelected = false;
      };
    }
  )
  .filter("myFilter", function () {
    function getPriceForFiltering(product) {
      return product.sale
        ? product.productDetails[0].prices.reduce((max, curr) =>
            new Date(max.createdDate) > new Date(curr.createdDate) ? max : curr
          ).price *
            (1 - product.discountPrice)
        : product.productDetails[0].prices[0].price;
    }

    return function (items, search) {
      var filterItems = {
        search: search,
        result: [],
      };

      if (!search.needFilter) {
        return items;
      }

      var isCategoryChecked = Object.values(search.category).some(Boolean);
      var isBrandChecked = Object.values(search.brand).some(Boolean);
      var isPriceRangeValid =
        search.minPrice !== null && search.maxPrice !== null;

      angular.forEach(
        items,
        function (value, key) {
          var isInCategory =
            !isCategoryChecked || search.category[value.category.categoryName];
          var isInBrand =
            !isBrandChecked ||
            search.brand[value.manufacturer.manufacturerName];
          var isWithinPriceRange =
            !isPriceRangeValid ||
            (getPriceForFiltering(value) >= search.minPrice &&
              getPriceForFiltering(value) <= search.maxPrice);

          if (isInCategory && isInBrand && isWithinPriceRange) {
            this.result.push(value);
          }
        },
        filterItems
      );
      return filterItems.result;
    };
  });
