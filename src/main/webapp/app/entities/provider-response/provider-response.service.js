(function() {
    'use strict';
    angular
        .module('ovasApp')
        .factory('ProviderResponse', ProviderResponse);

    ProviderResponse.$inject = ['$resource'];

    function ProviderResponse ($resource) {
        var resourceUrl =  'api/provider-responses/:id';

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
