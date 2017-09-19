(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('RequestTryDeleteController',RequestTryDeleteController);

    RequestTryDeleteController.$inject = ['$uibModalInstance', 'entity', 'RequestTry'];

    function RequestTryDeleteController($uibModalInstance, entity, RequestTry) {
        var vm = this;

        vm.requestTry = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RequestTry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
