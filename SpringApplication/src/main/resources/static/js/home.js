app.controller('homeController', function($http, $rootScope, $scope) {
	$rootScope.getPosts = function(){
		$http({
			method: 'GET',
			url: '/locations/'+$rootScope.employee.location.locationId+'/posts',
		}).then(function successCallback(response){
			$rootScope.locationPosts = response.data;
		}, function errorCallback(response){
		});
	};
	
	$scope.init = function(){
		$scope.getPosts();
	}
});