(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ServiceSecurityDialogController', ServiceSecurityDialogController);

    ServiceSecurityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ServiceSecurity', 'CommunicationStandard', 'SecurityParams', 'ProviderCommand'];

    function ServiceSecurityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ServiceSecurity, CommunicationStandard, SecurityParams, ProviderCommand) {
        var vm = this;

        vm.serviceSecurity = entity;
        vm.clear = clear;
        vm.save = save;
        vm.communicationstandards = CommunicationStandard.query();
        vm.securityparams = SecurityParams.query();
        vm.providercommands = ProviderCommand.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.serviceSecurity.id !== null) {
                ServiceSecurity.update(vm.serviceSecurity, onSaveSuccess, onSaveError);
            } else {
                ServiceSecurity.save(vm.serviceSecurity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ovasApp:serviceSecurityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
