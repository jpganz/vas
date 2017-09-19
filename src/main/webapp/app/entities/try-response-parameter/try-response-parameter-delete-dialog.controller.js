(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('TryResponseParameterDeleteController',TryResponseParameterDeleteController);

    TryResponseParameterDeleteController.$inject = ['$uibModalInstance', 'entity', 'TryResponseParameter'];

    function TryResponseParameterDeleteController($uibModalInstance, entity, TryResponseParameter) {
        var vm = this;

        vm.tryResponseParameter = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TryResponseParameter.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
