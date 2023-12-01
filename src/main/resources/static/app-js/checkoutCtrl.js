var apiKey = "f777fb49-835e-11ee-b394-8ac29577e80e";
app.controller(
  "checkoutCtrl",
  function ($scope, $rootScope, ApiService, $http) {
    let items = [];
    $rootScope.totalFeeShip = 0;
    $rootScope.totalPay = 0;
    $rootScope.getCheckedProducts.forEach((e) => {
      items.push({
        name: e.productDTO.productName,
        quantity: e.quantityInCart,
        height: 1,
        weight: 1,
        length: 1,
        width: 1,
      });
    });
    console.log(items);
    $rootScope.raw = {
      service_type_id: 2,
      from_district_id: 1572,
      to_district_id: null,
      to_ward_code: "",
      height: 1,
      length: 1,
      weight: 1,
      width: 1,
      insurance_value: 0,
      coupon: null,
      items: items,
    };
    console.log("checkoutCtrl");
    console.log($rootScope.getCheckedProducts);

    $scope.selectedAddress = null;
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
    $scope.calculateShippingFee = function (provinceName) {
      console.log(provinceName);
      if (provinceName != '') {
        var index = $scope.provinces.findIndex(function (province) {
          return province.ProvinceName === JSON.parse(provinceName).city;
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
              $rootScope.toDistrictId = $scope.districts.filter(
                (d) => d.DistrictName == JSON.parse(provinceName).district
              );
              $rootScope.raw.to_district_id =
                $rootScope.toDistrictId[0].DistrictID;
              console.log($rootScope.raw);
              var index2 = $scope.districts.findIndex(function (district) {
                return (
                  district.DistrictName === JSON.parse(provinceName).district
                );
              });

              if ($rootScope.toDistrictId != undefined) {
                console.log("ward");
                $http
                  .get(
                    "https://online-gateway.ghn.vn/shiip/public-api/master-data/ward",
                    {
                      headers: {
                        Token: apiKey,
                      },
                      params: {
                        district_id: $scope.toDistrictId[0].DistrictID,
                      },
                    }
                  )
                  .then(function (response) {
                    $scope.wards = response.data.data;
                    console.log($scope.wards);
                    $scope.wardCode = $scope.wards.filter(
                      (d) => d.WardName == JSON.parse(provinceName).ward
                    );
                    console.log($scope.wardCode[0].WardCode);
                    $rootScope.raw.to_ward_code = $scope.wardCode[0].WardCode;
                    console.log($rootScope.raw);
                    $http({
                      method: "POST",
                      url: "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee",
                      headers: {
                        "Content-Type": "application/json",
                        Token: "f777fb49-835e-11ee-b394-8ac29577e80e",
                        ShopId: "4698401",
                      },
                      data: {
                        service_type_id: $rootScope.raw.service_type_id,
                        from_district_id: $rootScope.raw.from_district_id,
                        to_district_id: $rootScope.raw.to_district_id,
                        to_ward_code: $rootScope.raw.to_ward_code,
                        height: $rootScope.raw.height,
                        length: $rootScope.raw.length,
                        Weight: $rootScope.raw.weight,
                        width: $rootScope.raw.width,
                        insurance_value: $rootScope.raw.insurance_value,
                        coupon: $rootScope.raw.coupon,
                        items: $rootScope.raw.items,
                      },
                    })
                      .then(function (response) {
                        console.log(response.data.data);
                        $rootScope.totalFeeShip = response.data.data.total;
                        $rootScope.totalPay =
                          parseFloat($rootScope.totalFeeShip) +
                          parseFloat($rootScope.getTotalPrice());
                      })
                      .catch(function (err) {
                        console.log(err);
                      });
                  })
                  .catch(function (error) {
                    console.error("Error fetching wards:", error);
                  });
              }
            })
            .catch(function (error) {
              console.error("Error fetching districts:", error);
            });
        }
      }
    };
  }
);