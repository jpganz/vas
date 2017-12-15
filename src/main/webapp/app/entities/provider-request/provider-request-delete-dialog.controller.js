(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderRequestDeleteController',ProviderRequestDeleteController);

    ProviderRequestDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProviderRequest'];

    function ProviderRequestDeleteController($uibModalInstance, entity, ProviderRequest) {
        var vm = this;

        vm.providerRequest = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProviderRequest.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
