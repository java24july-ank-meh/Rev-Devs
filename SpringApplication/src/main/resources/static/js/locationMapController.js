app.controller('locationMapController', function($rootScope, $scope,$http, $routeParams, $window) {
		$scope.init = function(){
			//$window.locationId = $routeParams.loc;
			
			$http({
				method: 'GET',
				url: '/locations/'+$routeParams.loc,
			}).then(function successCallback(response){
				$window.locationLat = response.data.lattitude;
				$window.locationLng = response.data.longitude;
				
			}, function errorCallback(response){
			});
		};
});