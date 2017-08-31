var app = angular.module("myApp", ["ngRoute"]);
app.config(function($routeProvider) {
	$routeProvider
	.when("/", {
		templateUrl : "home.html",
		activetab: 'home',
		controller: 'homeController'
	})
	.when("/profile/:emp", {
		templateUrl : "profile.html",
		activetab: 'profile'
	})
	.when("/explore", {
		templateUrl : "mainMap.html",
		activetab: 'explore'
	})
	.when("/location/:loc", {
		templateUrl : "view_location.html",
		activetab: 'location'
	});
});

app.run(function($rootScope, $http, $location, $window){
	$rootScope.employee = null;
	$rootScope.locationPosts = null;
	$rootScope.types = null;
	
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
			url: '/locations/'+$rootScope.employee.location.locationId+'/posts',
		}).then(function successCallback(response){
			$rootScope.locationPosts = response.data;
		}, function errorCallback(response){
		});
	};
	
	$rootScope.submitPost = function(locationId, currentPost) {
		
		let typeId = isNaN(parseInt(currentPost.typeId)) ? null : parseInt(currentPost.typeId);
		
		let data = $.param({
				"typeId": typeId,
				"locationId": locationId,
				"content": currentPost.comment,
				"longitude": $window.searchLongitude,
				"lattitude": $window.searchLattitude
		})
		
		console.log(data);
		$http({
			method: 'POST',
			url: "/posts",
			data: data,
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
			}
		}).then(function (response) {
			console.log("Request was successful")
			currentPost.comment = "";
			$rootScope.getPosts();
		}, function(error) {
			console.log("Request was unsuccessful for post")
		});
	
		
	};
	
	$rootScope.getPostTypes = function() {
		$http({
			method: 'GET',
			url: '/postTypes',
		}).then(function successCallback(response){
			$rootScope.types = response.data;
		}, function errorCallback(response){
		});
	};	
	$rootScope.getCurrentUser();
	$rootScope.getPostTypes();
});