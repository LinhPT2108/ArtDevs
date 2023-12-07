app.controller("changePasswordCtrl", function ($scope, ApiService, $rootScope) {
  console.log("changePasswordCtrl");
  $scope.form = {
    currentPassword: "",
    newPassword: "",
    confirmPassword: "",
  };

  $scope.validatePassword = function (password) {
    if (password == "") return true;
    if (password != null) {
      if (password.length < 6) {
        return false;
      }
      if (!/[a-zA-Z]/.test(password)) {
        return false;
      }
    }
    return true;
  };

  $scope.submitForm = function () {
    console.log($scope.form);
    ApiService.callApi("POST", "/rest/account/change-password", $scope.form)
      .then(function (response) {
        console.log(response.currentPasswordError);
        if (response.currentPasswordError != undefined && response != 500) {
          $("#currentPassError2").html(response.currentPasswordError);
        } else {
          $rootScope.userLogin = response;
          console.log($rootScope.userLogin);
          Swal.fire({
            title: "Đổi mật khẩu thành công",
            text: "Mật khẩu đã được thay đổi",
            icon: "success",
          });
          $scope.form = {
            currentPassword: "",
            newPassword: "",
            confirmPassword: "",
          };
        }
      })
      .catch(function (err) {
        console.log(err);
      });
  };

  $scope.focus = function () {
    console.log(123123);
    $("#currentPassError2").html("");
  };
});
