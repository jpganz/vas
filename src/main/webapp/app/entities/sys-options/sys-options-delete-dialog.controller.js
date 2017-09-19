(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('SysOptionsDeleteController',SysOptionsDeleteController);

    SysOptionsDeleteController.$inject = ['$uibModalInstance', 'entity', 'SysOptions'];

    function SysOptionsDeleteController($uibModalInstance, entity, SysOptions) {
        var vm = this;

        vm.sysOptions = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SysOptions.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
