app.controller("profileCtrl", function ($scope, $rootScope, ApiService, $http) {
  console.log("profileCtrl");

  $scope.updateProfile = function () {
    console.log("click update");
    console.log($rootScope.userLogin);
    var fullname = $("#fullname").val();
    var avatarValue = $("#image").val();
    console.log(avatarValue);
    console.log(fullname);
    var isError = false;

    if (fullname == "" || fullname.length < 3) {
      $("#fullnameError").text("Vui lòng nhập họ tên");
      isError = true;
    } else if (
      !/^[a-zA-Z\sàáạãảăắằẳẵặâấầẩẫậèéẹẽẻêếềểễệđìíịĩỉòóọõỏôốồổỗộơớờởỡợùúụũủưứừửữựỳýỵỹỷÀÁẠÃẢĂẮẰẲẴẶÂẤẦẨẪẬÈÉẸẼẺÊẾỀỂỄỆĐÌÍỊĨỈÒÓỌÕỎÔỐỒỔỖỘƠỚỜỞỠỢÙÚỤŨỦƯỨỪỬỮỰỲÝỴỸỶ']*$/u.test(
        fullname
      )
    ) {
      $("#fullnameError").text(
        "Họ và tên không được chứa số hoặc ký tự đặc biệt"
      );
      isError = true;
    } else {
      $rootScope.userLogin.fullname = fullname;
      isError = false;
    }

    if (avatarValue != "") {
      console.log("đã chọn ảnh");

      const imageInput = document.getElementById("image");

      const file = imageInput.files[0];
      console.log(file);
      const formData = new FormData();
      formData.append("avatar", file);

      const request = $http({
        method: "PUT",
        url: "/rest/account/update-avatar/" + $rootScope.userLogin.accountId,
        headers: {
          transformRequest: angular.identity,
          "Content-Type": undefined,
        },
        data: formData,
      });
      request
        .then((response) => {
          console.log(response.data);
          $rootScope.userLogin.image = response.data.avatarName;
        })
        .catch((error) => {
          console.log(error);
        });
    }
    console.log(isError);
    if (!isError) {
      ApiService.callApi(
        "PUT",
        "/rest/account/" + $rootScope.userLogin.accountId,
        $rootScope.userLogin
      )
        .then(function (response) {
          console.log(response);
          console.log($rootScope.userLogin);

          $("#fullnameError").text("");
          Swal.fire({
            icon: "success",
            title: "Cập nhật thành công",
            text: "Thông tin cá nhân đã được cập nhật !",
            showConfirmButton: true,
          });
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
});
