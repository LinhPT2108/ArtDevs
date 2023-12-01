app.controller("loginCtrl", function ($scope, $location) {
  var url = $location.search().error;
  console.log(url);
  if (url) {
    $scope.messError = "Tài khoản hoặc mật khẩu không chính xác";
  }else{
    $scope.messError = null;
  }
  console.log("loginCtrl");
});
