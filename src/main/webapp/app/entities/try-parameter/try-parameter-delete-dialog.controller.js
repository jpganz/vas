(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('TryParameterDeleteController',TryParameterDeleteController);

    TryParameterDeleteController.$inject = ['$uibModalInstance', 'entity', 'TryParameter'];

    function TryParameterDeleteController($uibModalInstance, entity, TryParameter) {
        var vm = this;

        vm.tryParameter = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TryParameter.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
