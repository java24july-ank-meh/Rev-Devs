var app = angular.module("myApp", ["ngRoute"]);
app.config(function($routeProvider) {
	$routeProvider
	.when("/", {
		templateUrl : "home.html",
		activetab: 'home'
	})
	.when("/profile", {
		templateUrl : "profile.html",
		activetab: 'profile'
	})
	.when("/explore", {
		templateUrl : "mainMap.html",
		activetab: 'explore'
	});
});

app.run(function($rootScope, $http, $location){
	$rootScope.employee = null;
	
	$rootScope.logout = function(){
		$http({
			method: 'GET',
			url: '/authentication/logout'
		}).then(function successCallback(response){
			let success = response.data.success;
			if(success){
				window.location.href = "/";
			}
		}, function errorCallback(response){
		});
	};
	
	$rootScope.getCurrentUser = function(){
		$http({
			method: 'GET',
			url: '/authentication/user',
		}).then(function successCallback(response){
			let success = response.data.success;
			if(success){
				$rootScope.employee = response.data.employee;
			} else {
				window.location.href = "/";
			}
		}, function errorCallback(response){
		});
	};
	$rootScope.getCurrentUser();
});