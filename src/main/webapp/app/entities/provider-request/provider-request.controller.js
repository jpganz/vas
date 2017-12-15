(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderRequestController', ProviderRequestController);

    ProviderRequestController.$inject = ['ProviderRequest'];

    function ProviderRequestController(ProviderRequest) {

        var vm = this;

        vm.providerRequests = [];

        loadAll();

        function loadAll() {
            ProviderRequest.query(function(result) {
                vm.providerRequests = result;
                vm.searchQuery = null;
            });
        }
    }
})();
