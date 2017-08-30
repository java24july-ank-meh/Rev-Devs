app.controller("exploreController", function($window, $scope,$rootScope,$http,$location){
	$scope.setLocation = function(){
		$http({
			method: 'POST',
			url: '/authentication/set-location',
			data: 'city='+$window.setLocationCity,
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).then(function successCallback(response){
			let success = response.data.success;
			if(success) $rootScope.getCurrentUser();
		}, function errorCallback(response){
			$rootScope.status = response.data.error + ": " + response.data.exception;
		});
	};
});