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
	})
	.when("/location", {
		templateUrl : "view_location.html",
		activetab: 'location'
	});
});

app.run(function($rootScope, $http, $location){
	$rootScope.employee = null;
	$rootScope.employeePosts = null;
	$rootScope.locationPosts = null;
	
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
				$rootScope.getPosts();
			} else {
				window.location.href = "/";
			}
		}, function errorCallback(response){
		});
	};
	
	$rootScope.getPosts = function(){
		$http({
			method: 'GET',
			url: '/employees/posts',
		}).then(function successCallback(response){
			$rootScope.employeePosts = response.data;
		}, function errorCallback(response){
		});
		
		$http({
			method: 'GET',
			url: '/locations/'+$rootScope.employee.location.locationId+'/posts',
		}).then(function successCallback(response){
			$rootScope.locationPosts = response.data;
		}, function errorCallback(response){
		});
	};
	
	$rootScope.getCurrentUser();
});