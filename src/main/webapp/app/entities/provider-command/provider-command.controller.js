(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderCommandController', ProviderCommandController);

    ProviderCommandController.$inject = ['ProviderCommand'];

    function ProviderCommandController(ProviderCommand) {

        var vm = this;

        vm.providerCommands = [];

        loadAll();

        function loadAll() {
            ProviderCommand.query(function(result) {
                vm.providerCommands = result;
                vm.searchQuery = null;
            });
        }
    }
})();
