(function() {
    'use strict';

    angular
        .module('ovasApp')
        .controller('CommunicationStandardController', CommunicationStandardController);

    CommunicationStandardController.$inject = ['CommunicationStandard'];

    function CommunicationStandardController(CommunicationStandard) {

        var vm = this;

        vm.communicationStandards = [];

        loadAll();

        function loadAll() {
            CommunicationStandard.query(function(result) {
                vm.communicationStandards = result;
                vm.searchQuery = null;
            });
        }
    }
})();
