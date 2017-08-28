app.controller("exploreController", function($scope,$rootScope,$http,$location){
	$scope.setLocation = function(city,lat,long){
		console.log(city);
		console.log(lat);
		console.log(long);
		console.log($rootScope.employee);
	};
	
});