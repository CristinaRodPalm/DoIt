(function() {
    'use strict';
    angular
        .module('doitApp')
        .factory('Reto', Reto);

    Reto.$inject = ['$resource', 'DateUtils'];

    function Reto ($resource, DateUtils) {
        var resourceUrl =  'api/retos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true, url:'api/retosOrder'},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.horaPublicacion = DateUtils.convertDateTimeFromServer(data.horaPublicacion);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
