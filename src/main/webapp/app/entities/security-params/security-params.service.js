(function() {
    'use strict';
    angular
        .module('ovasApp')
        .factory('SecurityParams', SecurityParams);

    SecurityParams.$inject = ['$resource'];

    function SecurityParams ($resource) {
        var resourceUrl =  'api/security-params/:id';

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
