(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('TryResponseParameterController', TryResponseParameterController);

    TryResponseParameterController.$inject = ['TryResponseParameter'];

    function TryResponseParameterController(TryResponseParameter) {

        var vm = this;

        vm.tryResponseParameters = [];

        loadAll();

        function loadAll() {
            TryResponseParameter.query(function(result) {
                vm.tryResponseParameters = result;
                vm.searchQuery = null;
            });
        }
    }
})();
