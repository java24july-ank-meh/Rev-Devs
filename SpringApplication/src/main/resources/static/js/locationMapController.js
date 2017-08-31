app.controller('locationMapController', function($rootScope, $scope,$http, $routeParams, $window) {
		$scope.init = function(){
			$http({
				method: 'GET',
				url: '/locations/'+$routeParams.loc,
			}).then(function successCallback(response){
				$window.getLocation($routeParams.loc, response.data.lattitude, response.data.longitude);
			}, function errorCallback(response){
			});
		};
});