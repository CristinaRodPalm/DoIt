(function() {
    'use strict';
    angular
        .module('doitApp')
        .factory('UserExt', UserExt);

    UserExt.$inject = ['$resource', 'DateUtils'];

    function UserExt ($resource, DateUtils) {
        var resourceUrl =  'api/user-exts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaNacimiento = DateUtils.convertDateTimeFromServer(data.fechaNacimiento);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
