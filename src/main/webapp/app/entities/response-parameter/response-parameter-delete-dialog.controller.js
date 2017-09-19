(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ResponseParameterDeleteController',ResponseParameterDeleteController);

    ResponseParameterDeleteController.$inject = ['$uibModalInstance', 'entity', 'ResponseParameter'];

    function ResponseParameterDeleteController($uibModalInstance, entity, ResponseParameter) {
        var vm = this;

        vm.responseParameter = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ResponseParameter.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
