(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('TryParameterController', TryParameterController);

    TryParameterController.$inject = ['TryParameter'];

    function TryParameterController(TryParameter) {

        var vm = this;

        vm.tryParameters = [];

        loadAll();

        function loadAll() {
            TryParameter.query(function(result) {
                vm.tryParameters = result;
                vm.searchQuery = null;
            });
        }
    }
})();
