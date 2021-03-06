(function (ng) {
    var mod = ng.module("restaurantesModule");
    mod.constant("restaurantesContext", "api/restaurantes");
    mod.controller("restaurantesCtrl", ['$scope', '$http', 'restaurantesContext', '$state', 
        function ($scope, $http, restaurantesContext, $state) {
            $scope.elements = [];
            $scope.actNit = $state.params.restauranteNit;
            $http.get("http://localhost:8080/puntosfidelidad-web/api/restaurantes")
                    .then(function (response) {
                        $scope.elements = response.data;
            });
            if (($state.params.restauranteNit !== undefined) && ($state.params.restauranteNit !== null)) {
                $http.get(restaurantesContext + '/' + $state.params.restauranteNit).then(function (response) {
                $scope.actual = response.data;
                });
            }
        }
    ]);
})(window.angular);