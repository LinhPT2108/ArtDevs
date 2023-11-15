var apiKey = "f777fb49-835e-11ee-b394-8ac29577e80e";

app.controller("addressCtrl", function ($scope, ApiService, $http, $timeout) {
  console.log("addressCtrl");

  // Tỉnh/thành phố
  $http
    .get(
      "https://online-gateway.ghn.vn/shiip/public-api/master-data/province",
      {
        headers: {
          Token: apiKey,
        },
      }
    )
    .then(function (response) {
      $scope.provinces = response.data.data;
      console.log($scope.provinces);
    })
    .catch(function (error) {
      console.error("Error fetching provinces:", error);
    });

  // Quận/huyện
  $scope.getDistricts = function (provinceName) {
    $('#districtInput').val('');
    console.log($('#district'))
    $('#wardInput').val('');
    var index = $scope.provinces.findIndex(function (province) {
      return province.ProvinceName === provinceName;
    });
    console.log(index);
    if ($scope.provinces[index]) {
      $http
        .get(
          "https://online-gateway.ghn.vn/shiip/public-api/master-data/district",
          {
            headers: {
              Token: apiKey,
            },
            params: {
              province_id: $scope.provinces[index].ProvinceID,
            },
          }
        )
        .then(function (response) {
          $scope.districts = response.data.data;
          console.log($scope.districts);
        })
        .catch(function (error) {
          console.error("Error fetching districts:", error);
        });
    }
  };

  // xã/huyện
  $scope.getWards = function (districtName) {
    $('#wardInput').val('');

    var index = $scope.districts.findIndex(function (district) {
      return district.DistrictName === districtName;
    });

    console.log(index);

    if ($scope.districts[index]) {
      $http
        .get(
          "https://online-gateway.ghn.vn/shiip/public-api/master-data/ward",
          {
            headers: {
              Token: apiKey,
            },
            params: {
              district_id: $scope.districts[index].DistrictID,
            },
          }
        )
        .then(function (response) {
          $scope.wards = response.data.data;
          console.log($scope.wards);
        })
        .catch(function (error) {
          console.error("Error fetching wards:", error);
        });
    }
  };
});
