(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('CommunicationStandardDialogController', CommunicationStandardDialogController);

    CommunicationStandardDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CommunicationStandard'];

    function CommunicationStandardDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CommunicationStandard) {
        var vm = this;

        vm.communicationStandard = entity;
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
            if (vm.communicationStandard.id !== null) {
                CommunicationStandard.update(vm.communicationStandard, onSaveSuccess, onSaveError);
            } else {
                CommunicationStandard.save(vm.communicationStandard, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ovasApp:communicationStandardUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
