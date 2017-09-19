(function() {
    'use strict';
    angular
        .module('ovasApp')
        .factory('Request', Request);

    Request.$inject = ['$resource', 'DateUtils'];

    function Request ($resource, DateUtils) {
        var resourceUrl =  'api/requests/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.nextTryBy = DateUtils.convertLocalDateFromServer(data.nextTryBy);
                        data.dateTime = DateUtils.convertLocalDateFromServer(data.dateTime);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.nextTryBy = DateUtils.convertLocalDateToServer(copy.nextTryBy);
                    copy.dateTime = DateUtils.convertLocalDateToServer(copy.dateTime);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.nextTryBy = DateUtils.convertLocalDateToServer(copy.nextTryBy);
                    copy.dateTime = DateUtils.convertLocalDateToServer(copy.dateTime);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
