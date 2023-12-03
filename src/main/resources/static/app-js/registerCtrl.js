var host = "http://localhost:8080";
app.controller("registerCtrl", function($scope, $http) {
	$scope.formData = {};
	$scope.formErrors = {};

	$scope.formData.roles = ["user"];
	$scope.formData.accountId = getAccountId();

	$scope.submitRegister = function() {
		$scope.formErrors = {};

		if (!$scope.formData.email || !isValidEmail($scope.formData.email)) {
			$scope.formErrors.email = true;
		}

		if (!$scope.formData.fullname || !isValidFullname($scope.formData.fullname)) {
			$scope.formErrors.fullname = true;
		}

		if (!$scope.formData.password) {
			$scope.formErrors.password = true;
		}

		if (!$scope.formData.remember) {
			$scope.formErrors.remember = true;
		}

		if (
			!$scope.formData.repassword ||
			$scope.formData.repassword !== $scope.formData.password
		) {
			$scope.formErrors.repassword = true;
		}

		$http
			.get(host + "/rest/account/findAccount/" + $scope.formData.email)
			.then(function(response) {
				console.log(response);
				$scope.formErrors.validateEmail = true;
			}).catch(function(error) {
				if (Object.keys($scope.formErrors).length > 0) {
					return;
				} else {
					$http
						.post(host + "/rest/account", $scope.formData)
						.then(function(response) {
							window.location.href =
								host + "/account/verify-code/" + response.data.accountId;
						})
						.catch(function(error) {
							console.error("Error:", error);
						});
				}
			})


	};

	function isValidEmail(email) {
		const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		return emailRegex.test(email);
	}

	function isValidFullname(fullname) {
		// Biểu thức chính quy kiểm tra tính hợp lệ của trường fullname
		const fullnameRegex = /^[^\d\s@#]+(?:\s[^\d\s@#]+)*$/;
		return fullnameRegex.test(fullname);
	}

	function getAccountId() {
		const randomString = uuidv4().replace(/-/g, "");
		const randomPart = randomString.substring(0, 8);
		const timestampPart = String(Date.now());
		const accountId = randomPart + timestampPart;
		return accountId;
	}

	function uuidv4() {
		return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(
			/[xy]/g,
			function(c) {
				const r = (Math.random() * 16) | 0,
					v = c === "x" ? r : (r & 0x3) | 0x8;
				return v.toString(16);
			}
		);
	}
});
