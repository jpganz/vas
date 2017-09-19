(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderResponseDeleteController',ProviderResponseDeleteController);

    ProviderResponseDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProviderResponse'];

    function ProviderResponseDeleteController($uibModalInstance, entity, ProviderResponse) {
        var vm = this;

        vm.providerResponse = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProviderResponse.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
