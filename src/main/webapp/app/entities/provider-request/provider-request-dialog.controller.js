(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderRequestDialogController', ProviderRequestDialogController);

    ProviderRequestDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProviderRequest', 'ProviderCommand', 'Request'];

    function ProviderRequestDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProviderRequest, ProviderCommand, Request) {
        var vm = this;

        vm.providerRequest = entity;
        vm.clear = clear;
        vm.save = save;
        vm.providercommands = ProviderCommand.query();
        vm.requests = Request.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.providerRequest.id !== null) {
                ProviderRequest.update(vm.providerRequest, onSaveSuccess, onSaveError);
            } else {
                ProviderRequest.save(vm.providerRequest, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ovasApp:providerRequestUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
