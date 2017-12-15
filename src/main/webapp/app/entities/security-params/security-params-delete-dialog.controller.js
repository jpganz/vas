(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('SecurityParamsDeleteController',SecurityParamsDeleteController);

    SecurityParamsDeleteController.$inject = ['$uibModalInstance', 'entity', 'SecurityParams'];

    function SecurityParamsDeleteController($uibModalInstance, entity, SecurityParams) {
        var vm = this;

        vm.securityParams = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SecurityParams.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
