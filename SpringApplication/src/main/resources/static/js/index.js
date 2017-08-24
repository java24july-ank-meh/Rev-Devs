let app = angular.module("myApp", ["ngRoute"]);
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
		templateUrl : "explore.html",
		activetab: 'explore'
	});
});

app.run(function($rootScope, $http, $location){
	
});