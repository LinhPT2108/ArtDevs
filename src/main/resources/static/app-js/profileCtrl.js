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
      $rootScope.userLogin.image = avatarValue;
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
