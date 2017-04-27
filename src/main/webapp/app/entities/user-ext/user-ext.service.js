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
            'allUsers': { method: 'GET', isArray: true, url:'api/all-users-ext'},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaNacimiento = DateUtils.convertDateTimeFromServer(data.fechaNacimiento);
                        data.nacimiento = DateUtils.convertLocalDateFromServer(data.nacimiento);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.nacimiento = DateUtils.convertLocalDateToServer(copy.nacimiento);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.nacimiento = DateUtils.convertLocalDateToServer(copy.nacimiento);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
