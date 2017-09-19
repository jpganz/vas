(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderResponseController', ProviderResponseController);

    ProviderResponseController.$inject = ['ProviderResponse'];

    function ProviderResponseController(ProviderResponse) {

        var vm = this;

        vm.providerResponses = [];

        loadAll();

        function loadAll() {
            ProviderResponse.query(function(result) {
                vm.providerResponses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
