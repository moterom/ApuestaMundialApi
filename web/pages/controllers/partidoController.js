app.controller('partidoController', ['$http','$scope','$rootScope','$location', '$routeParams','$filter',	 function($http, $scope, $rootScope, $location,$routeParams,$filter){
	
	$scope.getAllEquipos = function(){
		$http({
            method: "GET",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/Equipo/getEquipos',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (data, status) {
           $scope.equipos=data.data;
        });
		
	};	


	$scope.setPartido = function () {
        $http({
            method: "POST",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/Partido/setPartido',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            data: { 
                equipo_a: $scope.equipoA.id_equipo,
                equipo_b: $scope.equipoB.id_equipo,
                fecha: $scope.fechaPartido.getTime()
            }
        }).then(function (data, status) {
           $scope.guardado="guardado exitosamente";
        });
    };

    $scope.clear = function () {
		$scope.guardado=""
	};

}]);