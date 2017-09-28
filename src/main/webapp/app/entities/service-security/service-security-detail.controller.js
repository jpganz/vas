(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ServiceSecurityDetailController', ServiceSecurityDetailController);

    ServiceSecurityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ServiceSecurity', 'CommunicationStandard', 'SecurityParams', 'ProviderCommand'];

    function ServiceSecurityDetailController($scope, $rootScope, $stateParams, previousState, entity, ServiceSecurity, CommunicationStandard, SecurityParams, ProviderCommand) {
        var vm = this;

        vm.serviceSecurity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:serviceSecurityUpdate', function(event, result) {
            vm.serviceSecurity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
