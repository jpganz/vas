(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('RequestParameterDeleteController',RequestParameterDeleteController);

    RequestParameterDeleteController.$inject = ['$uibModalInstance', 'entity', 'RequestParameter'];

    function RequestParameterDeleteController($uibModalInstance, entity, RequestParameter) {
        var vm = this;

        vm.requestParameter = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RequestParameter.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
