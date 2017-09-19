(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('CommandDetailController', CommandDetailController);

    CommandDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Command'];

    function CommandDetailController($scope, $rootScope, $stateParams, previousState, entity, Command) {
        var vm = this;

        vm.command = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:commandUpdate', function(event, result) {
            vm.command = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
