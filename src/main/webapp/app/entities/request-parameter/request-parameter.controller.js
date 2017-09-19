(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('RequestParameterController', RequestParameterController);

    RequestParameterController.$inject = ['RequestParameter'];

    function RequestParameterController(RequestParameter) {

        var vm = this;

        vm.requestParameters = [];

        loadAll();

        function loadAll() {
            RequestParameter.query(function(result) {
                vm.requestParameters = result;
                vm.searchQuery = null;
            });
        }
    }
})();
