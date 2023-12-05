var host = "http://localhost:8080";
app.controller("verifyCtrl", function($scope, $routeParams, $http) {
	var accountId = $routeParams.accountId;

	$scope.formData = {};
	$scope.formErrors = {};

	$scope.submitVerify = function () {
        if (!$scope.formData.verifyCode) {
            $scope.formErrors.verifyCode = true;
            return;
        }

        var verifyCode = $scope.formData.verifyCode;

        $http.put(host + '/account/verify-code/' + accountId, verifyCode)
            .then(function (response) {
                console.log(response);
				Swal.fire({
                  icon: "success",
                  title: "Thông báo",
                  text: response.data.message,
                  showConfirmButton: true,
                });
                $("#modalAddress").modal("hide");
				
                window.location.href = host + "/account/login";
            })
            .catch(function (error) {
				console.log(error);
				Swal.fire({
                  icon: "error",
                  title: "Thông báo",
                  text: error.data.message,
                  showConfirmButton: true,
                });
            });
    };
});
