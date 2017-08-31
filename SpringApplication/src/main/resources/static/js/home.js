app.controller('homeController', function($http, $rootScope, $scope, $window) {
	$scope.post = {};
	$scope.post.typeId = undefined;
	$scope.post.comment = "";
	$scope.post.hotSpotAddress = "";
	$scope.types = [];
	$scope.posts = [];
	
	
	$http.get("/postTypes")
		.then(function(response) {
			$scope.types = response.data;
		}, function(error) {
			console.log("there was an error fetching the type ids")
		});
	
	$scope.submitPost = function($event) {
		
		let typeId = isNaN(parseInt($scope.post.typeId)) ? null : parseInt($scope.post.typeId);
		
		let data = $.param({
				"typeId": typeId,
				"locationId": $rootScope.employee.location.locationId,
				"content": $scope.post.comment,
				"longitude": $window.searchLongitude,
				"lattitude": $window.searchLattitude
		})
		
		console.log(data);
		$http({
			method: 'POST',
			url: "/posts",
			data: data,
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
			}
		}).then(function (response) {
			console.log("Request was successful")
			$scope.post.comment = "";
			$rootScope.getPosts();
		}, function(error) {
			console.log("Request was unsuccessful for post")
		});
	
		
	}
	
	
});