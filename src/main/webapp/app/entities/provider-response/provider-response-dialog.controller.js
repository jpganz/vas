(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderResponseDialogController', ProviderResponseDialogController);

    ProviderResponseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProviderResponse', 'ProviderCommand', 'ResponseParameter'];

    function ProviderResponseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProviderResponse, ProviderCommand, ResponseParameter) {
        var vm = this;

        vm.providerResponse = entity;
        vm.clear = clear;
        vm.save = save;
        vm.providercommands = ProviderCommand.query();
        vm.responseparameters = ResponseParameter.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.providerResponse.id !== null) {
                ProviderResponse.update(vm.providerResponse, onSaveSuccess, onSaveError);
            } else {
                ProviderResponse.save(vm.providerResponse, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ovasApp:providerResponseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
