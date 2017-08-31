app.controller('locationController', function($rootScope, $scope,$http, $routeParams) {
	$scope.location = null;
	$scope.posts = null;
	$scope.types = null;
	$scope.users = null;
	
	$scope.typeIndex = null;
	
	$scope.init = function(){
		$http({
			method: 'GET',
			url: '/locations/'+$routeParams.loc+'/posts',
		}).then(function successCallback(response){
			$scope.posts = response.data;
		}, function errorCallback(response){
		});
		
		$http({
			method: 'GET',
			url: '/locations/'+$routeParams.loc,
		}).then(function successCallback(response){
			$scope.location = response.data;
		}, function errorCallback(response){
		});
		
		$http({
			method: 'GET',
			url: '/postTypes',
		}).then(function successCallback(response){
			$scope.types = response.data;
		}, function errorCallback(response){
		});
		
		$http({
			method: 'GET',
			url: '/locations/'+$routeParams.loc+'/users',
		}).then(function successCallback(response){
			$scope.users = response.data;
		}, function errorCallback(response){
		});
	}
});	