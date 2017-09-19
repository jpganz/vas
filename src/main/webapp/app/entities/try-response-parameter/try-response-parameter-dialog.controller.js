(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('TryResponseParameterDialogController', TryResponseParameterDialogController);

    TryResponseParameterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TryResponseParameter'];

    function TryResponseParameterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TryResponseParameter) {
        var vm = this;

        vm.tryResponseParameter = entity;
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
            if (vm.tryResponseParameter.id !== null) {
                TryResponseParameter.update(vm.tryResponseParameter, onSaveSuccess, onSaveError);
            } else {
                TryResponseParameter.save(vm.tryResponseParameter, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ovasApp:tryResponseParameterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
