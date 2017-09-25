(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('RequestParameterDetailController', RequestParameterDetailController);

    RequestParameterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RequestParameter', 'TryParameter', 'ProviderCommand'];

    function RequestParameterDetailController($scope, $rootScope, $stateParams, previousState, entity, RequestParameter, TryParameter, ProviderCommand) {
        var vm = this;

        vm.requestParameter = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:requestParameterUpdate', function(event, result) {
            vm.requestParameter = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
