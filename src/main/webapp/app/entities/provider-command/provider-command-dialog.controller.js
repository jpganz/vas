(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderCommandDialogController', ProviderCommandDialogController);

    ProviderCommandDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProviderCommand', 'Provider', 'CommunicationStandard', 'ProviderResponse', 'RequestParameter', 'Command', 'ServiceSecurity'];

    function ProviderCommandDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProviderCommand, Provider, CommunicationStandard, ProviderResponse, RequestParameter, Command, ServiceSecurity) {
        var vm = this;

        vm.providerCommand = entity;
        vm.clear = clear;
        vm.save = save;
        vm.providers = Provider.query();
        vm.communicationstandards = CommunicationStandard.query();
        vm.providerresponses = ProviderResponse.query();
        vm.requestparameters = RequestParameter.query();
        vm.commands = Command.query();
        vm.servicesecurities = ServiceSecurity.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.providerCommand.id !== null) {
                ProviderCommand.update(vm.providerCommand, onSaveSuccess, onSaveError);
            } else {
                ProviderCommand.save(vm.providerCommand, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ovasApp:providerCommandUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
