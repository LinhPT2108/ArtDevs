var host = "http://localhost:8080";
app.controller("loginCtrl", function($scope, $location, $timeout, $window, $http) {
	var url = $location.search().error;
	$scope.formdata = {};
	console.log(url);
	if (url) {
		$scope.messError = "Tài khoản hoặc mật khẩu không chính xác";
	} else {
		$scope.messError = null;
	}
	var profile;

	$scope.loginWithGoogle = function() {
		$timeout(function() {
			gapi.load('auth2', function() {
				gapi.auth2.init({
					client_id: '360026796698-s34eom4dksakni5no02d44qgihgph78b.apps.googleusercontent.com',
					scope: "email",
					redirect_uri: 'http://127.0.0.1:8080/login/oauth2/code/google',
					plugin_name: 'AE Shop',
				});
				gapi.auth2.getAuthInstance().signIn().then(
					function(googleUser) {
						profile = googleUser.getBasicProfile();
						if (profile) {
							const account = {
								"accountId": profile.getId(),
								"fullname": profile.getName(),
								"password": profile.getId(),
								"email": profile.getEmail(),
								"roles": [
									"user"
								]
							}
							$http
								.get(host + "/rest/account/findAccount/" + account.email)
								.then(function(response) {
									console.log("account: ", response)
									$window.location.href = '/account/login';
									$scope.formdata.email = profile.getEmail();
									$scope.formdata.password = profile.getId();
									$timeout(function() {
										document.getElementById("customer_login").submit();
									}, 0);
								}).catch(function(error) {
									$http
										.post(host + "/rest/account-with-google", account)
										.then(function(response) {
											$window.location.href = '/account/login';
											$scope.formdata.email = profile.getEmail();
											$scope.formdata.password = profile.getId();
											console.log("email 2: ", $scope.formdata.email);
											$timeout(function() {
												document.getElementById("customer_login").submit();
											}, 0);

										})
										.catch(function(error) {
											console.error("Error:", error);
										});
								})
						}
					},
					function(error) {
						console.error("Google login error:", error);
						// Xử lý khi có lỗi đăng nhập
					}
				);
			});
		});
	};

});
