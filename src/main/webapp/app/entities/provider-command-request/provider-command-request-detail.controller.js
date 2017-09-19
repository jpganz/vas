(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderCommandRequestDetailController', ProviderCommandRequestDetailController);

    ProviderCommandRequestDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProviderCommandRequest'];

    function ProviderCommandRequestDetailController($scope, $rootScope, $stateParams, previousState, entity, ProviderCommandRequest) {
        var vm = this;

        vm.providerCommandRequest = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:providerCommandRequestUpdate', function(event, result) {
            vm.providerCommandRequest = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
