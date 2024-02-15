app.controller('personaController', ['$http','$scope','$rootScope','$location', function($http, $scope, $rootScope, $location){
	//$scope.tratoSelect = {};
	$scope.hoy = new Date();



	$scope.getAllPersonas = function(){
		$http({
            method: "GET",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/persona/getPersonas',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (data, status) {
           
           $scope.personas=data.data;
           console.log($scope.personas);
        });
		
	};	

}]);