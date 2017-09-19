(function() {
    'use strict';
    angular
        .module('ovasApp')
        .factory('ProviderCommandRequest', ProviderCommandRequest);

    ProviderCommandRequest.$inject = ['$resource'];

    function ProviderCommandRequest ($resource) {
        var resourceUrl =  'api/provider-command-requests/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
