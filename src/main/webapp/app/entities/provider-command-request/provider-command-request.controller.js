(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderCommandRequestController', ProviderCommandRequestController);

    ProviderCommandRequestController.$inject = ['ProviderCommandRequest'];

    function ProviderCommandRequestController(ProviderCommandRequest) {

        var vm = this;

        vm.providerCommandRequests = [];

        loadAll();

        function loadAll() {
            ProviderCommandRequest.query(function(result) {
                vm.providerCommandRequests = result;
                vm.searchQuery = null;
            });
        }
    }
})();
