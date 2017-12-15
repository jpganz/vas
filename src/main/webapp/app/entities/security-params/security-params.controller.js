(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('SecurityParamsController', SecurityParamsController);

    SecurityParamsController.$inject = ['SecurityParams'];

    function SecurityParamsController(SecurityParams) {

        var vm = this;

        vm.securityParams = [];

        loadAll();

        function loadAll() {
            SecurityParams.query(function(result) {
                vm.securityParams = result;
                vm.searchQuery = null;
            });
        }
    }
})();
