app.controller('equipoController', ['$http','$scope','$rootScope','$location', '$routeParams','$filter',	 function($http, $scope, $rootScope, $location,$routeParams,$filter){
	
	$scope.hoy = $filter("date")(new Date(), 'yyyy-MM-dd');
	
	

	$scope.selectedEquipo = {
		codigo: null,
		nombre: " "
	};

		
	$scope.getEquipos = function () {
		$http({
            method: "POST",
            url: 'http://'+window.location.host+'/ApuestaMundialApi/webresources/alertaTrato/getdataPost',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		    transformRequest: function(obj) {
		        var str = [];
		        for(var p in obj)
		        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
		        return str.join("&");
		    },
            data: { numero:$routeParams.numero,
            		codent: 1
            }
        }).then(function (data, status) {
            $scope.alertasTrato=data.data;
            $scope.getFuncionarios();
        });
	};

	
}]);