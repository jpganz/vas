(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('RequestParameterDialogController', RequestParameterDialogController);

    RequestParameterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RequestParameter', 'TryParameter', 'ProviderCommand'];

    function RequestParameterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RequestParameter, TryParameter, ProviderCommand) {
        var vm = this;

        vm.requestParameter = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tryparameters = TryParameter.query();
        vm.providercommands = ProviderCommand.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.requestParameter.id !== null) {
                RequestParameter.update(vm.requestParameter, onSaveSuccess, onSaveError);
            } else {
                RequestParameter.save(vm.requestParameter, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ovasApp:requestParameterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
