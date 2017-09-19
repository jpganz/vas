(function() {
    'use strict';
    angular
        .module('ovasApp')
        .factory('ResponseParameter', ResponseParameter);

    ResponseParameter.$inject = ['$resource'];

    function ResponseParameter ($resource) {
        var resourceUrl =  'api/response-parameters/:id';

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
