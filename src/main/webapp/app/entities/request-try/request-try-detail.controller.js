(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('RequestTryDetailController', RequestTryDetailController);

    RequestTryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RequestTry', 'Request', 'TryResponseParameter'];

    function RequestTryDetailController($scope, $rootScope, $stateParams, previousState, entity, RequestTry, Request, TryResponseParameter) {
        var vm = this;

        vm.requestTry = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:requestTryUpdate', function(event, result) {
            vm.requestTry = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
