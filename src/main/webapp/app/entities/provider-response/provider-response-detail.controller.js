(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderResponseDetailController', ProviderResponseDetailController);

    ProviderResponseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProviderResponse'];

    function ProviderResponseDetailController($scope, $rootScope, $stateParams, previousState, entity, ProviderResponse) {
        var vm = this;

        vm.providerResponse = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:providerResponseUpdate', function(event, result) {
            vm.providerResponse = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
