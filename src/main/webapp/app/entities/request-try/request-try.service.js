(function() {
    'use strict';
    angular
        .module('ovasApp')
        .factory('RequestTry', RequestTry);

    RequestTry.$inject = ['$resource'];

    function RequestTry ($resource) {
        var resourceUrl =  'api/request-tries/:id';

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
