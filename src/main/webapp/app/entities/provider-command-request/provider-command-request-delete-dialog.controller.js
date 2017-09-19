(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderCommandRequestDeleteController',ProviderCommandRequestDeleteController);

    ProviderCommandRequestDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProviderCommandRequest'];

    function ProviderCommandRequestDeleteController($uibModalInstance, entity, ProviderCommandRequest) {
        var vm = this;

        vm.providerCommandRequest = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProviderCommandRequest.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
