(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('RequestTryDialogController', RequestTryDialogController);

    RequestTryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RequestTry'];

    function RequestTryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RequestTry) {
        var vm = this;

        vm.requestTry = entity;
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
            if (vm.requestTry.id !== null) {
                RequestTry.update(vm.requestTry, onSaveSuccess, onSaveError);
            } else {
                RequestTry.save(vm.requestTry, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ovasApp:requestTryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
