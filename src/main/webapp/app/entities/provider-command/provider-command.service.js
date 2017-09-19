(function() {
    'use strict';
    angular
        .module('ovasApp')
        .factory('ProviderCommand', ProviderCommand);

    ProviderCommand.$inject = ['$resource'];

    function ProviderCommand ($resource) {
        var resourceUrl =  'api/provider-commands/:id';

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
