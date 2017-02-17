(function() {
    'use strict';
    angular
        .module('doitApp')
        .factory('LikesReto', LikesReto);

    LikesReto.$inject = ['$resource', 'DateUtils'];

    function LikesReto ($resource, DateUtils) {
        var resourceUrl =  'api/likes-retos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.horaLike = DateUtils.convertDateTimeFromServer(data.horaLike);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
