(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('TryParameterDetailController', TryParameterDetailController);

    TryParameterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TryParameter'];

    function TryParameterDetailController($scope, $rootScope, $stateParams, previousState, entity, TryParameter) {
        var vm = this;

        vm.tryParameter = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:tryParameterUpdate', function(event, result) {
            vm.tryParameter = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
