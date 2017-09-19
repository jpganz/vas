(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('CommandDialogController', CommandDialogController);

    CommandDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Command'];

    function CommandDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Command) {
        var vm = this;

        vm.command = entity;
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
            if (vm.command.id !== null) {
                Command.update(vm.command, onSaveSuccess, onSaveError);
            } else {
                Command.save(vm.command, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ovasApp:commandUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
