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
	})
	.when("/locationMap/:loc", {
		templateUrl : "locationMap.html"
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
			} else {
				window.location.href = "/";
			}
		}, function errorCallback(response){
		});
	};
	
	$rootScope.submitPost = function(locationId, currentPost, getPostsCallback) {
		
		let typeId = isNaN(parseInt(currentPost.typeId)) ? null : parseInt(currentPost.typeId);
		
		let data = $.param({
				"typeId": typeId,
				"locationId": locationId,
				"content": currentPost.comment,
				"longitude": $window.searchLongitude,
				"lattitude": $window.searchLattitude
		});
		
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
			getPostsCallback();
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
	
	$rootScope.onStart = function(){
		$rootScope.getCurrentUser();
		$rootScope.getPostTypes();
	};
});