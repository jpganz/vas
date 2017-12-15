(function() {
    'use strict';
    angular
        .module('ovasApp')
        .factory('ProviderRequest', ProviderRequest);

    ProviderRequest.$inject = ['$resource'];

    function ProviderRequest ($resource) {
        var resourceUrl =  'api/provider-requests/:id';

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
