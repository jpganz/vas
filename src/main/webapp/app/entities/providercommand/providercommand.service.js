(function() {
    'use strict';
    angular
        .module('ovasApp')
        .factory('Providercommand', Providercommand);

    Providercommand.$inject = ['$resource'];

    function Providercommand ($resource) {
        var resourceUrl =  'api/providercommands/:id';

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
