var app=angular.module("alertaApp", ['ngRoute','ui.bootstrap'])
.config(function($routeProvider) {
    $routeProvider
    .when('/', {
        templateUrl : 'views/inicio.html'
    })
    .when('/sorteo', {
        templateUrl : 'views/sorteo.html'
        ,controller: 'sorteoController'
    })
    .when('/premio', {
        templateUrl : 'views/premio.html'
        ,controller: 'premioController'
    })
    .when('/marcador', {
        templateUrl : 'views/marcador.html'
        ,controller: 'marcadorController'
    })
    .when('/informes', {
        templateUrl : 'views/informes.html'
        ,controller: 'informeController'
    })
    .when('/partido', {
        templateUrl : 'views/partido.html'
        ,controller: 'partidoController'
    })
    .when('/reciboApuesta', {
            templateUrl : 'views/reporteApuesta.html'
        ,controller: 'reciboApuestaController'
    })
    .otherwise({
      redirectTo : '/'  
    });
});
