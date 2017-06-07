(function() {
    'use strict';
    angular
        .module('doitApp')
        .factory('ParticipacionReto', ParticipacionReto);

    ParticipacionReto.$inject = ['$resource', 'DateUtils'];

    function ParticipacionReto ($resource, DateUtils) {
        var resourceUrl =  'api/participacion-retos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
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
            'create': { method:'POST' , url:'api/participacion-retos'}
        });
    }
})();
