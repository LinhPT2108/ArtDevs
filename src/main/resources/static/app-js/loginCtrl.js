
app.factory('AuthService', function($http, $location, jwtHelper) {
	var authService = {};

	authService.login = function(email, password) {
		return $http.post('http://localhost:8080/api/login', { email: email, password: password })
			.then(function(response) {
				localStorage.setItem('token', response.data.token);
				localStorage.setItem('refreshToken', response.data.refeshToken);
				return response.data;
			});
	};

	authService.getToken = function() {
		return localStorage.getItem('token');
	};

	authService.isTokenValid = function() {
		var token = localStorage.getItem('token');

		if (token) {
			var tokenData = parseToken(token);
			var currentTimestamp = Math.floor(Date.now() / 1000);

			return tokenData && tokenData.exp > currentTimestamp;
		}

		return false;
	};

	authService.redirectToHome = function() {
		$location.path('/ArtDevs');
	};

	authService.redirectToLogin = function() {
		$location.path('/login');
	};

	authService.parseToken = function(token) {
		try {
			return jwtHelper.decodeToken(token);
		} catch (error) {
			console.error('Lỗi khi giải mã token:', error);
			return null;
		}
	};

	return authService;
});

app.controller('loginCtrl', function($scope, AuthService) {
	$scope.auth = { email: '', password: '' };
	$scope.sumitForm = function() {
		console.log('Đăng nhập thành công:', $scope.auth);
		AuthService.login($scope.auth.email, $scope.auth.password)
			.then(function(data) {
				console.log('Đăng nhập thành công:', data);

				if (AuthService.isTokenValid()) {
					AuthService.redirectToHome();
				} else {
					AuthService.redirectToLogin();
				}
			})
			.catch(function(error) {
				console.error('Lỗi đăng nhập:', error);
			});
	};
	
	console.log('Đăng nhập thành công:123');
});

/*app.config(function($locationProvider) {
	$locationProvider.html5Mode(true);
});*/
