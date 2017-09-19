(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('TryResponseParameterDetailController', TryResponseParameterDetailController);

    TryResponseParameterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TryResponseParameter'];

    function TryResponseParameterDetailController($scope, $rootScope, $stateParams, previousState, entity, TryResponseParameter) {
        var vm = this;

        vm.tryResponseParameter = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ovasApp:tryResponseParameterUpdate', function(event, result) {
            vm.tryResponseParameter = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
