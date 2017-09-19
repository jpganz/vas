(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ProviderController', ProviderController);

    ProviderController.$inject = ['Provider'];

    function ProviderController(Provider) {

        var vm = this;

        vm.providers = [];

        loadAll();

        function loadAll() {
            Provider.query(function(result) {
                vm.providers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
