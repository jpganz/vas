(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('CommunicationStandardDetailController', CommunicationStandardDetailController);

    CommunicationStandardDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CommunicationStandard'];

    function CommunicationStandardDetailController($scope, $rootScope, $stateParams, previousState, entity, CommunicationStandard) {
        var vm = this;

        vm.communicationStandard = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:communicationStandardUpdate', function(event, result) {
            vm.communicationStandard = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
