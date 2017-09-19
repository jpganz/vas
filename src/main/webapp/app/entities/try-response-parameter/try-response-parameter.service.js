(function() {
    'use strict';
    angular
        .module('ovasApp')
        .factory('TryResponseParameter', TryResponseParameter);

    TryResponseParameter.$inject = ['$resource'];

    function TryResponseParameter ($resource) {
        var resourceUrl =  'api/try-response-parameters/:id';

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
