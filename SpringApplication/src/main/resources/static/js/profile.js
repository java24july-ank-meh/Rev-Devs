app.controller("profileController", function($scope,$rootScope,$http,$location){
	$scope.editMode = false;
	$scope.editButtonName = "EDIT";
	$scope.status = "";
	$scope.statusColor = "red";
	$scope.cancelButton = function(){
		$rootScope.getCurrentUser();
		$scope.editMode = false;
		$scope.editButtonName = "EDIT";
		$scope.status = "";
		$scope.statusColor = "red";
	};
	$scope.editButton = function(){
		let _username = $rootScope.employee.username;
		let _email = $rootScope.employee.email;
		let _fname = $rootScope.employee.fname;
		let _lname = $rootScope.employee.lname;
		
		if($scope.editMode){
			$http({
				method: 'POST',
				url: '/authentication/update-profile',
				data: 'username='+_username+'&email='+_email+'&fname='+_fname+'&lname='+_lname,
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).then(function successCallback(response){
				let success = response.data.success;
				if(success){
					$scope.status = "successfully updated account";
					$scope.statusColor = "green";
					$rootScope.getCurrentUser();
					$scope.editMode = false;
					$scope.editButtonName = "EDIT";
				} else {
					$scope.status = response.data.message;
					$scope.statusColor = "red";
				}
			}, function errorCallback(response){
				$scope.status = response.data.error + ": " + response.data.exception;
				$scope.statusColor = "red";
			});
		} else {
			$scope.editMode = true;
			$scope.editButtonName = "SAVE";
		}
	};
	
	/*
	 $scope.editButton = function(){
		let _username = $rootScope.employee.username;
		let _email = $rootScope.employee.email;
		let _fname = $rootScope.employee.fname;
		let _lname = $rootScope.employee.lname;
		
		let _loc = $rootScope.employee.location;
		let _com = $rootScope.employee.company;
		let _locId = 0;
		let _comId = 0;
		if(_loc != null) _locId = _loc.locationId;
		if(_com != null) _comId = _com.companyId;
		
		if($scope.editMode){
			$http({
				method: 'PUT',
				url: '/employees/'+$rootScope.employee.employeeId,
				data: 'username='+_username+'&email='+_email+'&fname='+_fname+'&lname='+_lname+'&locationId='+_locId+'&companyId='+_comId,
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).then(function successCallback(response){
				let success = response.data.success;
				if(success){
					$scope.status = "successfully updated account";
					$scope.statusColor = "green";
					$rootScope.getCurrentUser();
					$scope.editMode = false;
					$scope.editButtonName = "EDIT";
				} else {
					$scope.status = response.data.message;
					$scope.statusColor = "red";
				}
			}, function errorCallback(response){
				$scope.status = response.data.error + ": " + response.data.exception;
				$scope.statusColor = "red";
			});
		} else {
			$scope.editMode = true;
			$scope.editButtonName = "SAVE";
		}
	};
	 */
});