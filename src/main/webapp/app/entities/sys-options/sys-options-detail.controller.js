(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('SysOptionsDetailController', SysOptionsDetailController);

    SysOptionsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SysOptions'];

    function SysOptionsDetailController($scope, $rootScope, $stateParams, previousState, entity, SysOptions) {
        var vm = this;

        vm.sysOptions = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:sysOptionsUpdate', function(event, result) {
            vm.sysOptions = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
