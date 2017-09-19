(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ServiceSecurityController', ServiceSecurityController);

    ServiceSecurityController.$inject = ['ServiceSecurity'];

    function ServiceSecurityController(ServiceSecurity) {

        var vm = this;

        vm.serviceSecurities = [];

        loadAll();

        function loadAll() {
            ServiceSecurity.query(function(result) {
                vm.serviceSecurities = result;
                vm.searchQuery = null;
            });
        }
    }
})();
