var host = "http://localhost:8080";
app.controller("forgetPasswordCtrl", function($scope, $http) {
	$scope.formData = {};
	$scope.formErrors = {};
	$scope.account = {};

	$scope.submitForgetPassword = function() {
		$scope.formErrors = {};

		if (!$scope.formData.email || !isValidEmail($scope.formData.email)) {
			$scope.formErrors.isExistAccount = false;
			$scope.formErrors.email = true;
			return;
		}

		$http.get(host + "/rest/account/findAccount/" + $scope.formData.email)
			.then(function(response) {
				$scope.account = response.data;
				Swal.fire({
					icon: 'success',
					title: 'Xác thực tài khoản',
					text: "Bạn sẽ được chuyển sang trang xác thực tài khoản với email!",
					showConfirmButton: false,
					allowOutsideClick: false,
					allowEscapeKey: false,
					timer: 6000,
					timerProgressBar: true
				});

				$http.put(host + "/account/forgot-password/" + $scope.account.accountId, { email: $scope.formData.email })
					.then(function(response) {
						window.location.href = host + "/account/verify-code/" + $scope.account.accountId;
					})
					.catch(function(error) {
						console.error("Error:", error);
					});
			})
			.catch(function(error) {
				$scope.formErrors.email = false;
				$scope.formErrors.isExistAccount = true;
			});
	};


	function isValidEmail(email) {
		const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		return emailRegex.test(email);
	}
});
