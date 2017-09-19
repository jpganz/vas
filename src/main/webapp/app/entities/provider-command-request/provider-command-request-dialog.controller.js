(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderCommandRequestDialogController', ProviderCommandRequestDialogController);

    ProviderCommandRequestDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProviderCommandRequest'];

    function ProviderCommandRequestDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProviderCommandRequest) {
        var vm = this;

        vm.providerCommandRequest = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.providerCommandRequest.id !== null) {
                ProviderCommandRequest.update(vm.providerCommandRequest, onSaveSuccess, onSaveError);
            } else {
                ProviderCommandRequest.save(vm.providerCommandRequest, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ovasApp:providerCommandRequestUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
