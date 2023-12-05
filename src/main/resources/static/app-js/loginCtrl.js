app.controller("loginCtrl", function($scope, $location, $timeout, $window) {
	var url = $location.search().error;
	console.log(url);
	if (url) {
		$scope.messError = "Tài khoản hoặc mật khẩu không chính xác";
	} else {
		$scope.messError = null;
	}

	$scope.loginWithGoogle = function() {
		$timeout(function() {
			// Bắt đầu quá trình tải thư viện
			gapi.load('auth2', function() {
				// Khởi tạo thư viện
				gapi.auth2.init({
					client_id: '360026796698-s34eom4dksakni5no02d44qgihgph78b.apps.googleusercontent.com',
					scope: "email",
					redirect_uri: 'http://127.0.0.1:8080/login/oauth2/code/google',
					plugin_name: 'AE Shop',
				});

				gapi.auth2.getAuthInstance().signIn().then(
					function(googleUser) {
						// Xử lý sau khi đăng nhập thành công
						var profile = googleUser.getBasicProfile();
						console.log('ID: ' + profile.getId());
						console.log('Name: ' + profile.getName());
						console.log('Image URL: ' + profile.getImageUrl());
						console.log('Email: ' + profile.getEmail());
						$window.location.href = '/';
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
