(function() {
    'use strict';
    angular
        .module('ovasApp')
        .factory('SysOptions', SysOptions);

    SysOptions.$inject = ['$resource'];

    function SysOptions ($resource) {
        var resourceUrl =  'api/sys-options/:id';

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
