(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('SecurityParamsDialogController', SecurityParamsDialogController);

    SecurityParamsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SecurityParams', 'ServiceSecurity'];

    function SecurityParamsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SecurityParams, ServiceSecurity) {
        var vm = this;

        vm.securityParams = entity;
        vm.clear = clear;
        vm.save = save;
        vm.servicesecurities = ServiceSecurity.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.securityParams.id !== null) {
                SecurityParams.update(vm.securityParams, onSaveSuccess, onSaveError);
            } else {
                SecurityParams.save(vm.securityParams, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ovasApp:securityParamsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
