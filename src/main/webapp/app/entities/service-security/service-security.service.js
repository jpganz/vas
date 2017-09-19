(function() {
    'use strict';
    angular
        .module('ovasApp')
        .factory('ServiceSecurity', ServiceSecurity);

    ServiceSecurity.$inject = ['$resource'];

    function ServiceSecurity ($resource) {
        var resourceUrl =  'api/service-securities/:id';

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
