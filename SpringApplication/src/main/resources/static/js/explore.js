app.controller("exploreController", function($window, $scope,$rootScope,$http,$location){
	$scope.setLocation = function(){
		console.log($window.setLocationCity);
		console.log($window.setLocationLat);
		console.log($window.setLocationLng);
		console.log($rootScope.employee);
	};
});