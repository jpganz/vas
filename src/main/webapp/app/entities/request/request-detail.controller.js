(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('RequestDetailController', RequestDetailController);

    RequestDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Request'];

    function RequestDetailController($scope, $rootScope, $stateParams, previousState, entity, Request) {
        var vm = this;

        vm.request = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:requestUpdate', function(event, result) {
            vm.request = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
