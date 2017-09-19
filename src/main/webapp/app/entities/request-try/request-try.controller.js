(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('RequestTryController', RequestTryController);

    RequestTryController.$inject = ['RequestTry'];

    function RequestTryController(RequestTry) {

        var vm = this;

        vm.requestTries = [];

        loadAll();

        function loadAll() {
            RequestTry.query(function(result) {
                vm.requestTries = result;
                vm.searchQuery = null;
            });
        }
    }
})();
