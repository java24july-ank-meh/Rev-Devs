app.controller("exploreController", function($window, $scope,$rootScope,$http,$location){
	$scope.setLocation = function(){
		$http({
			method: 'POST',
			url: '/authentication/set-location',
			data: 'location_id='+$window.location_id,
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).then(function successCallback(response){
			let success = response.data.success;
		}, function errorCallback(response){
			$rootScope.status = response.data.error + ": " + response.data.exception;
		});
	};
});