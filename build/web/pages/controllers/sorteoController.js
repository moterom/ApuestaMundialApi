app.controller('sorteoController', ['$http','$scope','$rootScope','$location', '$routeParams','$filter', 'apuestaFactory',	 function($http, $scope, $rootScope, $location,$routeParams,$filter,apuestaFactory){
	
	$scope.hoy = $filter("date")(new Date(), 'dd-MM-yyyy');
	
	$scope.SelectedCedula=null;

	$scope.selectedPersona = {
		cedula: null,
		nombre: " "
	};

	

	$scope.getPersonaByCedula = function () {
		$http({
            method: "POST",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/persona/getPersonaByCedula',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		    transformRequest: function(obj) {
		        var str = [];
		        for(var p in obj)
		        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
		        return str.join("&");
		    },
            data: { numero:$routeParams.numero,
            		cedula: $scope.SelectedCedula
            }
        }).then(function (data, status) {
            $scope.cedulaResult=data.data;
        });
	};

	$scope.getAllPartidos = function () {
		$http({
            method: "GET",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/Partido/getPartidosSinMarcador'
        }).then(function (data, status) {
        	console.log(data.data);
            $scope.partidosResult=data.data;
        });
	};


	$scope.setApuestas = function () {
		$http({
            method: "POST",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/Apuesta/setApuestas',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		    transformRequest: function(obj) {
		        var str = [];
		        for(var p in obj)
		        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
		        return str.join("&");
		    },
            data: { 
            	id_partido: $scope.partidoSelected.id_partido,
            	marcador_a: $scope.SmarcadorA,
            	marcador_b: $scope.SmarcadorB,
            	cedula: $scope.SelectedCedula
            }
        }).then(function (data, status) {
        	apuestaFactory.apuestaDetails.cedula=$scope.SelectedCedula;
        	apuestaFactory.apuestaDetails.nombre=$scope.cedulaResult;
        	apuestaFactory.apuestaDetails.equipoA=$scope.partidoSelected.equipo_a;
			apuestaFactory.apuestaDetails.equipoB=$scope.partidoSelected.equipo_b;
			apuestaFactory.apuestaDetails.marcadorA=$scope.SmarcadorA;
			apuestaFactory.apuestaDetails.marcadorB= $scope.SmarcadorB;
        	$location.url('/reciboApuesta');
            $scope.SelectedCedula="";
            $scope.SmarcadorA="";
            $scope.SmarcadorB="";
            $scope.cedulaResult="";
        });
	};
	
	$scope.setPersona = function () {
		$http({
            method: "POST",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/persona/setPersona',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		    transformRequest: function(obj) {
		        var str = [];
		        for(var p in obj)
		        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
		        return str.join("&");
		    },
            data: { 
            	cedula: $scope.SelectedCedula,
            	nombre: $scope.cedulaResult
            }
        }).then(function (data, status) {
           $scope.setApuestas();
        }); 
	};
}]);