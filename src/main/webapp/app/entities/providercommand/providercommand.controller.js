(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProvidercommandController', ProvidercommandController);

    ProvidercommandController.$inject = ['Providercommand'];

    function ProvidercommandController(Providercommand) {

        var vm = this;

        vm.providercommands = [];

        loadAll();

        function loadAll() {
            Providercommand.query(function(result) {
                vm.providercommands = result;
                vm.searchQuery = null;
            });
        }
    }
})();
