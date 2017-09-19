(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('SysOptionsDialogController', SysOptionsDialogController);

    SysOptionsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SysOptions'];

    function SysOptionsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SysOptions) {
        var vm = this;

        vm.sysOptions = entity;
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
            if (vm.sysOptions.id !== null) {
                SysOptions.update(vm.sysOptions, onSaveSuccess, onSaveError);
            } else {
                SysOptions.save(vm.sysOptions, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ovasApp:sysOptionsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
