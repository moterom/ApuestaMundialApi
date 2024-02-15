app.controller('marcadorController', ['$http','$scope','$rootScope','$location', '$routeParams','$filter',	 function($http, $scope, $rootScope, $location,$routeParams,$filter){
	
	$scope.getAllPartidos = function () {
		$http({
            method: "GET",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/Partido/getPartidosSinMarcador'
        }).then(function (data, status) {
            $scope.partidosResult=data.data; 
        });
	};

     $scope.getPremio = function () {
        $http({
            method: "POST",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/Partido/getPremioPorPartido',
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
            $scope.premio = data.data;
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
            if($scope.Acertados.length==0){
                $scope.NoAcertados="Ningun acertado Para el partido seleccionado!!!";
            }else {
                $scope.NoAcertados="";
            }
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
                id_partido: $scope.partidoSelected.id_partido,
            }
        }).then(function (data, status) {
            $scope.Apuestas = data.data;
        });
    };

	$scope.updateMarcador = function () {
        $http({
            method: "POST",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/Partido/updateMarcadorPartido',
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
                marcador_b: $scope.SmarcadorB
            }
        }).then(function (data, status) {
           $scope.getAllAcertados();
        });
    };

    $scope.getGanador = function () {
        $http({
            method: "POST",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/Partido/getGanador',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            data: { 
                id_partido: $scope.partidoSelected.id_partido
            }
        }).then(function (data, status) {
           $scope.ganador=data.data;
           $scope.getAllPartidos();
        });
    };
	
}]);