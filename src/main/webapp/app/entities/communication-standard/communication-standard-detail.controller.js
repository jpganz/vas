(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('CommunicationStandardDetailController', CommunicationStandardDetailController);

    CommunicationStandardDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CommunicationStandard', 'ProviderCommand'];

    function CommunicationStandardDetailController($scope, $rootScope, $stateParams, previousState, entity, CommunicationStandard, ProviderCommand) {
        var vm = this;

        vm.communicationStandard = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:communicationStandardUpdate', function(event, result) {
            vm.communicationStandard = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
