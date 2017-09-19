(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ServiceSecurityDeleteController',ServiceSecurityDeleteController);

    ServiceSecurityDeleteController.$inject = ['$uibModalInstance', 'entity', 'ServiceSecurity'];

    function ServiceSecurityDeleteController($uibModalInstance, entity, ServiceSecurity) {
        var vm = this;

        vm.serviceSecurity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ServiceSecurity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
