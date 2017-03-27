(function() {
    'use strict';
    angular
        .module('doitApp')
        .factory('Evento', Evento);

    Evento.$inject = ['$resource', 'DateUtils'];

    function Evento ($resource, DateUtils) {
        var resourceUrl =  'api/eventos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.hora = DateUtils.convertDateTimeFromServer(data.hora);
                        data.fechaEvento = DateUtils.convertDateTimeFromServer(data.fechaEvento);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
