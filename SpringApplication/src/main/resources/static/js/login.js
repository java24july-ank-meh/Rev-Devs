let app = angular.module("loginPageApp", ["ngRoute"]);

app.run(function($rootScope, $http, $location){
	$rootScope.status = "";
	
	$rootScope.login = function(username,password){
		$http({
			method: 'POST',
			url: '/authentication/login',
			data: 'username='+username+"&password="+password,
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).then(function successCallback(response){
			let success = response.data.success;
			if(success){
				window.location.href = "/home";
			} else {
				$rootScope.status= response.data.message;
			}
		}, function errorCallback(response){
			$rootScope.status= "ERROR";
		});
	};
	
	//Log in if the user has a session
	$rootScope.autoLogin = function(){
		$http({
			method: 'GET',
			url: '/authentication/user',
		}).then(function successCallback(response){
			let success = response.data.success;
			if(success){
				window.location.href = "/home";
			}
		}, function errorCallback(response){
		});
	};
	
	$rootScope.autoLogin();
});