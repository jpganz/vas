(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('TryParameterDialogController', TryParameterDialogController);

    TryParameterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TryParameter', 'Request', 'RequestParameter'];

    function TryParameterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TryParameter, Request, RequestParameter) {
        var vm = this;

        vm.tryParameter = entity;
        vm.clear = clear;
        vm.save = save;
        vm.requests = Request.query();
        vm.requestparameters = RequestParameter.query();

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
