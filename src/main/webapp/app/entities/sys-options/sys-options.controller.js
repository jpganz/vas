(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('SysOptionsController', SysOptionsController);

    SysOptionsController.$inject = ['SysOptions'];

    function SysOptionsController(SysOptions) {

        var vm = this;

        vm.sysOptions = [];

        loadAll();

        function loadAll() {
            SysOptions.query(function(result) {
                vm.sysOptions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
