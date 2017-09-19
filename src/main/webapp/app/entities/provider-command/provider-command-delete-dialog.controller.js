(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderCommandDeleteController',ProviderCommandDeleteController);

    ProviderCommandDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProviderCommand'];

    function ProviderCommandDeleteController($uibModalInstance, entity, ProviderCommand) {
        var vm = this;

        vm.providerCommand = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProviderCommand.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
