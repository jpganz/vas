(function() {
    'use strict';
    angular
        .module('ovasApp')
        .factory('RequestParameter', RequestParameter);

    RequestParameter.$inject = ['$resource'];

    function RequestParameter ($resource) {
        var resourceUrl =  'api/request-parameters/:id';

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
