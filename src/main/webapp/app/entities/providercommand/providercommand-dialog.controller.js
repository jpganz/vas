(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProvidercommandDialogController', ProvidercommandDialogController);

    ProvidercommandDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Providercommand', 'Provider', 'CommunicationStandard', 'Command'];

    function ProvidercommandDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Providercommand, Provider, CommunicationStandard, Command) {
        var vm = this;

        vm.providercommand = entity;
        vm.clear = clear;
        vm.save = save;
        vm.providers = Provider.query();
        vm.communicationstandards = CommunicationStandard.query();
        vm.commands = Command.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.providercommand.id !== null) {
                Providercommand.update(vm.providercommand, onSaveSuccess, onSaveError);
            } else {
                Providercommand.save(vm.providercommand, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ovasApp:providercommandUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
