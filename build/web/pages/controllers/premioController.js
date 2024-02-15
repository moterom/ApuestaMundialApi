app.controller('premioController', ['$http','$scope','$rootScope','$location', '$routeParams','$filter',	 function($http, $scope, $rootScope, $location,$routeParams,$filter){
	
	$scope.getAllPartidos = function () {
		$http({
            method: "GET",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/Partido/getPartidos'
        }).then(function (data, status) {
            $scope.partidosResult=data.data;
        });
	};

	$scope.getAllPremios = function () {
		$http({
            method: "GET",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/Premio/getPremios'
        }).then(function (data, status) {
            $scope.premiosResult=data.data;
            $scope.getAllPartidos();
        });
	};

	$scope.setApuestas = function () {
		$http({
            method: "POST",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/Premio/setPremio',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		    transformRequest: function(obj) {
		        var str = [];
		        for(var p in obj)
		        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
		        return str.join("&");
		    },
            data: { 
            	nombre: $scope.premioSelected
            }
        }).then(function (data, status) {
            $scope.premioSelected="";
            $scope.getAllPremios();
        });
	};

	$scope.updatePremioXPartido = function () {
		$http({
            method: "POST",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/Partido/updatePremioPorPartido',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		    transformRequest: function(obj) {
		        var str = [];
		        for(var p in obj)
		        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
		        return str.join("&");
		    },
            data: { 
            	id_partido: $scope.partidoSelected.id_partido,
            	id_premio: $scope.premioSelectedSelect.id_premio
            }
        }).then(function (data, status) {
              $scope.guardado="guardado exitosamente";
        });
	};

	$scope.clear = function () {
		$scope.guardado=""
	};
	
}]);