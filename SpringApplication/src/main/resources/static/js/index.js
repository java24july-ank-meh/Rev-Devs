let app = angular.module("myApp", ["ngRoute"]);
app.config(function($routeProvider) {
	$routeProvider
	.when("/", {
		templateUrl : "home.html"
	})
	.when("/profile", {
		templateUrl : "profile.html"
	})
	.when("/explore", {
		templateUrl : "explore.html"
	});
});

app.run(function($rootScope, $http, $location){
	
});