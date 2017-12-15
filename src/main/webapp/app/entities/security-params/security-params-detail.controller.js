(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('SecurityParamsDetailController', SecurityParamsDetailController);

    SecurityParamsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SecurityParams', 'ServiceSecurity'];

    function SecurityParamsDetailController($scope, $rootScope, $stateParams, previousState, entity, SecurityParams, ServiceSecurity) {
        var vm = this;

        vm.securityParams = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:securityParamsUpdate', function(event, result) {
            vm.securityParams = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
