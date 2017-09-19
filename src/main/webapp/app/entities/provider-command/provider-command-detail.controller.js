(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderCommandDetailController', ProviderCommandDetailController);

    ProviderCommandDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProviderCommand'];

    function ProviderCommandDetailController($scope, $rootScope, $stateParams, previousState, entity, ProviderCommand) {
        var vm = this;

        vm.providerCommand = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:providerCommandUpdate', function(event, result) {
            vm.providerCommand = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
