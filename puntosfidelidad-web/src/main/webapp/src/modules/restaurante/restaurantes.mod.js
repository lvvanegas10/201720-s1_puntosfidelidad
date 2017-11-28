(function (ng) {
    var mod = ng.module("restaurantesModule", []);
    mod.constant("restaurantesContext", "api/restaurantes");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/restaurante/';
            $urlRouterProvider.otherwise("/restaurantes");

            $stateProvider.state('restaurantes', {
                url: '/restaurantes',
                abstract: true,
                data: {
                    requireLogin: true
                },
                views: {
                    'info': {
                        templateUrl: basePath + 'restaurantes.html',
                        controller: 'restaurantesCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('restaurantesList', {
                url: 'restaurantes/list',
                data: {
                    requireLogin: true
                },
                views: {
                    'info': {
                        controller: 'restaurantesCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'restaurantes.list.html'
                    }
                }
            }).state('restaurantesDetail', {
                url: '/{restauranteNit:string}/detail',
                parent: 'restaurantes',
                data: {
                    requireLogin: true
                },
                param: {
                    restauranteNit: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + 'restaurantes.detail.html',
                        controller: 'restaurantesCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('restaurantesProductos', {
                url: '/productos',
                parent: 'restaurantesDetail',
                data: {
                    requireLogin: true
                },
                param: {
                    restauranteNit: null
                },
                views: {
                    'extrasView': {
                        templateUrl: basePath + 'restaurantes.productos.html',
                        controller: 'restaurantesCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('restaurantesSucursales', {
                url: '/sucursales',
                parent: 'restaurantesDetail',
                data: {
                    requireLogin: true
                },
                param: {
                    restauranteNit: null
                },
                views: {
                    'extrasView': {
                        templateUrl: basePath + 'restaurantes.sucursales.html',
                        controller: 'restaurantesCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('restaurantesEventos', {
                url: '/eventos',
                parent: 'restaurantesDetail',
                data: {
                    requireLogin: true
                },
                param: {
                    restauranteNit: null
                },
                views: {
                    'extrasView': {
                        templateUrl: basePath + 'restaurantes.eventos.html',
                        controller: 'restaurantesCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            }).state('restaurantesPost', {
                url: '/create',
                parent: 'restaurantes',
                data: {
                    requireLogin: true
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/post/restaurantes.post.html',
                        controller: 'restaurantesPostCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('restaurantesUpdate', {
                url: '/update/{restauranteNit:string}',
                parent: 'restaurantes',
                data: {
                    requireLogin: true
                },
                param: {
                    restauranteNit: null
                },
                views: {
                    'extrasView': {
                        templateUrl: basePath + '/update/restaurantes.update.html',
                        controller: 'restaurantesUpdateCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('restaurantesDelete', {
                url: '/restaurantes/{restauranteNit:string}/delete',
                parent: 'restaurantes',
                data: {
                    requireLogin: true
                },
                param: {
                    restauranteNit: null
                },
                views: {
                    'info': {
                        templateUrl: basePath + 'delete/restaurantes.delete.html',
                        controller: 'restauranteDeleteCtrl'
                    }
                }
            });
        }]);

})(window.angular);
    