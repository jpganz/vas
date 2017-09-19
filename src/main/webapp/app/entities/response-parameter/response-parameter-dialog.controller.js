(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ResponseParameterDialogController', ResponseParameterDialogController);

    ResponseParameterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ResponseParameter', 'ProviderResponse'];

    function ResponseParameterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ResponseParameter, ProviderResponse) {
        var vm = this;

        vm.responseParameter = entity;
        vm.clear = clear;
        vm.save = save;
        vm.providerresponses = ProviderResponse.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.responseParameter.id !== null) {
                ResponseParameter.update(vm.responseParameter, onSaveSuccess, onSaveError);
            } else {
                ResponseParameter.save(vm.responseParameter, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ovasApp:responseParameterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
