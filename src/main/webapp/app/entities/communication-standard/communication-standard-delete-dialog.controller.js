(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('CommunicationStandardDeleteController',CommunicationStandardDeleteController);

    CommunicationStandardDeleteController.$inject = ['$uibModalInstance', 'entity', 'CommunicationStandard'];

    function CommunicationStandardDeleteController($uibModalInstance, entity, CommunicationStandard) {
        var vm = this;

        vm.communicationStandard = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CommunicationStandard.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
