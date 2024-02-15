app.controller('informeController', ['$http','$scope','$rootScope','$location', '$routeParams','$filter',	 function($http, $scope, $rootScope, $location,$routeParams,$filter){
	
	$scope.getGanadores = function(){
		$http({
            method: "GET",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/Partido/getGanadores',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (data, status) {
           
           $scope.ganadores=data.data;
        });
		
	};

	$scope.getAllPartidos = function () {
		$http({
            method: "GET",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/Partido/getPartidos'
        }).then(function (data, status) {
            $scope.partidosResult=data.data;
        });
	};


	$scope.getAllAcertados = function () {
        $http({
            method: "POST",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/Partido/getAcertados',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            data: { 
                id_partido: $scope.partidoSelected.id_partido,
            }
        }).then(function (data, status) {
            $scope.Acertados = data.data;
            $scope.getPremio();
        });
    };

    $scope.getAllApuestasXPartido = function () {
        $http({
            method: "POST",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/Partido/getApuestasPorPartido',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            data: { 
                id_partido: $scope.partidoSelectedApuesta.id_partido,
            }
        }).then(function (data, status) {
            $scope.Apuestas = data.data;
            console.log("estas son las apuestas",  $scope.Apuestas);
        });
    };

    $scope.getAllPersonas = function(){
		$http({
            method: "GET",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/persona/getPersonas',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (data, status) {
           
           $scope.personas=data.data;
        });
		
	};	
	
}]);