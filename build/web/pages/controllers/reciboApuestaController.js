app.controller('reciboApuestaController', ['$http','$scope','$rootScope','$location', '$routeParams','$filter', 'apuestaFactory',	 function($http, $scope, $rootScope, $location,$routeParams,$filter, apuestaFactory){
	
	$scope.hoy = $filter("date")(new Date(), 'yyyy-MM-dd');


			
	$scope.initVar = function () {
		console.log("facoty", apuestaFactory.apuestaDetails);
		$scope.cedula=apuestaFactory.apuestaDetails.cedula;
		$scope.nombre=apuestaFactory.apuestaDetails.nombre;
		$scope.equipoA=apuestaFactory.apuestaDetails.equipoA;
		$scope.equipoB=apuestaFactory.apuestaDetails.equipoB;
		$scope.marcadorA=apuestaFactory.apuestaDetails.marcadorA;
		$scope.marcadorB=apuestaFactory.apuestaDetails.marcadorB;
	};
}]);