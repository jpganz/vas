(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProvidercommandDeleteController',ProvidercommandDeleteController);

    ProvidercommandDeleteController.$inject = ['$uibModalInstance', 'entity', 'Providercommand'];

    function ProvidercommandDeleteController($uibModalInstance, entity, Providercommand) {
        var vm = this;

        vm.providercommand = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Providercommand.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
