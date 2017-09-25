(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProvidercommandDetailController', ProvidercommandDetailController);

    ProvidercommandDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Providercommand', 'Provider', 'CommunicationStandard', 'Command'];

    function ProvidercommandDetailController($scope, $rootScope, $stateParams, previousState, entity, Providercommand, Provider, CommunicationStandard, Command) {
        var vm = this;

        vm.providercommand = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:providercommandUpdate', function(event, result) {
            vm.providercommand = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
