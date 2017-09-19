(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('TryParameterDialogController', TryParameterDialogController);

    TryParameterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TryParameter'];

    function TryParameterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TryParameter) {
        var vm = this;

        vm.tryParameter = entity;
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
            if (vm.tryParameter.id !== null) {
                TryParameter.update(vm.tryParameter, onSaveSuccess, onSaveError);
            } else {
                TryParameter.save(vm.tryParameter, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ovasApp:tryParameterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
