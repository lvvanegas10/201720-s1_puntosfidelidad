(function (ng) {
    var mod = ng.module("restaurantesModule", []);
        mod.constant("restaurantesContext", "api/restaurantes");
        mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider, $scope) {
                var basePath = 'src/modules/restaurante/';
                $urlRouterProvider.otherwise("/restaurantes");
    
            $stateProvider.state('restaurantes', {
                url: '/restaurantes',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'restaurantes.html',
                        controller: 'restaurantesCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('restaurantesList', {
                url: 'restaurantes/list',
                views: {
                    'mainView': {
                        controller: 'restaurantesCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'restaurantes.list.html'
                    }
                }
            }).state('restaurantesDetail', {
                url: '/{restauranteNit:string}/detail',
                parent: 'restaurantes',
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
            }).state('restaurantesPost', {
                url: '/create',
                parent: 'restaurantes',
                views: {
                    'detailView': {
                        templateUrl: basePath + '/post/restaurantes.post.html',
                        controller: 'restaurantesPostCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('restaurantesUpdate', {
                url: '/update/{productoId:int}',
                parent: 'restaurantes',
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
                url: '/delete/{restauranteNit:string}',
                parent: 'restaurantesDetail',
                param: {
                    restauranteNit: null
                },
                views: {
                    'extrasView': {
                        templateUrl: basePath + '/delete/restaurantes.delete.html',
                        controller: 'restaurantesDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            }]);
    
    })(window.angular);
    