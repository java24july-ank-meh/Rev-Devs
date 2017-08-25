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
			let res = response.data;
			if(res.success){
				window.location.href = "/home";
			} else {
				$rootScope.status= res.message;
			}
		}, function errorCallback(response){
			$rootScope.status= "ERROR";
		});
	};
});