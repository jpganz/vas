(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ResponseParameterDetailController', ResponseParameterDetailController);

    ResponseParameterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ResponseParameter', 'ProviderResponse', 'TryResponseParameter'];

    function ResponseParameterDetailController($scope, $rootScope, $stateParams, previousState, entity, ResponseParameter, ProviderResponse, TryResponseParameter) {
        var vm = this;

        vm.responseParameter = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:responseParameterUpdate', function(event, result) {
            vm.responseParameter = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
