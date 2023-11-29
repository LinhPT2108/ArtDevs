
app.factory('AuthService', function($http, $location) {
    var authService = {};

    authService.login = function(email, password) {
        return $http.post('/api/login', { email: email, password: password })
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
            return jwt_decode(token);
        } catch (error) {
            console.error('Lỗi khi giải mã token:', error);
            return null;
        }
    };

    return authService;
});

app.controller('LoginController', function($scope, AuthService) {
    $scope.credentials = { email: '', password: '' };

    $scope.login = function() {
        AuthService.login($scope.credentials.email, $scope.credentials.password)
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
});

app.config(function($locationProvider) {
    $locationProvider.html5Mode(true);
});
