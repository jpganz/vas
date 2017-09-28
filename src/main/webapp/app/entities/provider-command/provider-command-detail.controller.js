(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderCommandDetailController', ProviderCommandDetailController);

    ProviderCommandDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProviderCommand', 'Provider', 'CommunicationStandard', 'ProviderResponse', 'RequestParameter', 'Command', 'ServiceSecurity'];

    function ProviderCommandDetailController($scope, $rootScope, $stateParams, previousState, entity, ProviderCommand, Provider, CommunicationStandard, ProviderResponse, RequestParameter, Command, ServiceSecurity) {
        var vm = this;

        vm.providerCommand = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:providerCommandUpdate', function(event, result) {
            vm.providerCommand = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
