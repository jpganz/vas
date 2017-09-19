(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('ResponseParameterController', ResponseParameterController);

    ResponseParameterController.$inject = ['ResponseParameter'];

    function ResponseParameterController(ResponseParameter) {

        var vm = this;

        vm.responseParameters = [];

        loadAll();

        function loadAll() {
            ResponseParameter.query(function(result) {
                vm.responseParameters = result;
                vm.searchQuery = null;
            });
        }
    }
})();
