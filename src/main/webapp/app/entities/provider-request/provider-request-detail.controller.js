(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderRequestDetailController', ProviderRequestDetailController);

    ProviderRequestDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProviderRequest', 'ProviderCommand', 'Request'];

    function ProviderRequestDetailController($scope, $rootScope, $stateParams, previousState, entity, ProviderRequest, ProviderCommand, Request) {
        var vm = this;

        vm.providerRequest = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:providerRequestUpdate', function(event, result) {
            vm.providerRequest = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
