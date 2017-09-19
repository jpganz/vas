(function() {
    'use strict';
    angular
        .module('ovasApp')
        .factory('CommunicationStandard', CommunicationStandard);

    CommunicationStandard.$inject = ['$resource'];

    function CommunicationStandard ($resource) {
        var resourceUrl =  'api/communication-standards/:id';

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
