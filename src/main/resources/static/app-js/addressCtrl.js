var apiKey = "f777fb49-835e-11ee-b394-8ac29577e80e";

app.controller(
  "addressCtrl",
  function ($scope, ApiService, $http, $timeout, $rootScope) {
    
    $scope.currentPage = 1;
    $scope.pageSize = 4;

    $scope.address = {
      phoneNumber: "",
      city: "",
      district: "",
      ward: "",
      specificValue: "",
    };
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
      if (typeApi != "PUT") {
        $("#districtInput").val("");
        $("#wardInput").val("");
      }
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
      console.log(districtName);
      if (typeApi != "PUT") {
        $("#wardInput").val("");
      }

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

    var typeApi;

    $scope.createAddress = function () {
      $("#btnCheck").attr(
        "href",
        "/account/" + $rootScope.userLogin.accountId + "/address"
      );
      $scope.address = {
        phoneNumber: "",
        city: "",
        district: "",
        ward: "",
        specificValue: "",
      };
      typeApi = "POST";
      $("#phoneNumber").attr("readonly", false);
    };
    function isValidPhoneNumber(phoneNumber) {
      const regexPhoneNumber = /(84|0[3|5|7|8|9])+([0-9]{8})\b/g;
      return phoneNumber.match(regexPhoneNumber) ? true : false;
    }

    function isDuplicatePhoneNumber(phoneNumber) {
      return $rootScope.userLogin.inforAddresses.some(
        (a) => a.phoneNumber === phoneNumber
      );
    }

    $scope.handleAddress = function (event, data) {
      event.preventDefault();
      console.log("handleAddress");
      console.log(data);
      console.log(typeApi);
      console.log($("#btnCheck").attr("href"));
      let url = "/rest" + $("#btnCheck").attr("href");
      let isValid = true;
      if (data.phoneNumber == "") {
        $scope.phoneNumberError = "Vui lòng nhập số điện thoại";
        isValid = false;
      } else if (!isValidPhoneNumber(data.phoneNumber)) {
        $scope.phoneNumberError = "Số điện thoại không hợp lệ";
        isValid = false;
      } else if (
        isDuplicatePhoneNumber(data.phoneNumber) &&
        typeApi == "POST"
      ) {
        $scope.phoneNumberError = "Số điện thoại đã tồn tại";
        isValid = false;
      } else {
        $scope.phoneNumberError = "";
        isValid = true;
      }

      if (data.city == "" || data.district == "" || data.ward == "") {
        $scope.addressError = "Vui lòng chọn địa chỉ";
        isValid = false;
      } else {
        $scope.addressError = "";
        isValid = true;
      }

      if (isValid) {
        ApiService.callApi(typeApi, url, data)
          .then(function (response) {
            if (typeApi == "POST") {
              if (response != 409) {
                $rootScope.userLogin.inforAddresses.unshift(response);
                console.log($rootScope.userLogin.inforAddresses);
                Swal.fire({
                  icon: "success",
                  title: "Thêm địa chỉ thành công",
                  text: "Thông tin địa chỉ đã được thêm !",
                  showConfirmButton: true,
                });
                $("#modalAddress").modal("hide");
              } else {
                $scope.phoneNumberError = "Số điện thoại đã tồn tại";
              }
            } else if (typeApi == "PUT") {
              if (response != 404) {
                var index = $rootScope.userLogin.inforAddresses.findIndex(
                  (a) => a.phoneNumber === response.phoneNumber
                );

                if (index !== -1) {
                  $rootScope.userLogin.inforAddresses[index] = response;
                }

                Swal.fire({
                  icon: "success",
                  title: "Cập nhật địa chỉ thành công",
                  text: "Thông tin địa chỉ đã được cập nhật !",
                  showConfirmButton: true,
                });
                $("#modalAddress").modal("hide");
              }
            }
          })
          .catch(function (error) {
            console.log(error);
            Swal.fire({
              icon: "warning",
              title: "Có lỗi xảy ra",
              text: "Vui  lòng thử lại !",
              showConfirmButton: true,
            });
          });
      }
    };

    $scope.handleDelete = function (event, phoneNumber, index) {
      event.preventDefault();
      console.log(phoneNumber, index);

      Swal.fire({
        title: "Bạn có muốn xóa địa chỉ này?",
        text: "Bạn sẽ không thể hoàn tác lại địa chỉ này!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Xóa!",
        cancelButtonText: "Hủy",
      }).then((result) => {
        if (result.isConfirmed) {
          ApiService.callApi(
            "DELETE",
            "/rest/account/" +
              $rootScope.userLogin.accountId +
              "/address/" +
              phoneNumber
          )
            .then(function (response) {
              console.log(response);
              if (response == 200) {
                $rootScope.userLogin.inforAddresses.splice(index, 1);
                console.log($rootScope.userLogin.inforAddresses);
                Swal.fire({
                  icon: "success",
                  title: "Xóa địa chỉ thành công",
                  text: "Địa chỉ đã được xóa !",
                  showConfirmButton: true,
                });
              }
            })
            .catch(function (error) {
              console.log(response);
            });
        }
      });
    };

    var tempAddress;

    $scope.handleUpdate = function (event, thisAddress, index) {
      event.preventDefault();
      tempAddress = index;
      typeApi = "PUT";
      $scope.editingAddress = angular.copy(thisAddress);
      $scope.address = thisAddress;
      console.log(thisAddress);
      $("#phoneNumber").attr("readonly", true);
      $("#btnCheck").attr(
        "href",
        "/account/" +
          $rootScope.userLogin.accountId +
          "/address/" +
          thisAddress.phoneNumber
      );
      $scope.getDistricts($scope.address.city);
      $timeout(function () {
        
      $scope.getWards(thisAddress.district);
      },400)
    };

    $scope.cancelUpdate = function () {
      console.log($scope.editingAddress);
      $rootScope.userLogin.inforAddresses[tempAddress] = $scope.editingAddress;
      tempAddress = null;
    };
  }
);
