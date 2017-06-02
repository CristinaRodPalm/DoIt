(function() {
    'use strict';
    angular
        .module('doitApp')
        .factory('InvitacionEvento', InvitacionEvento);

    InvitacionEvento.$inject = ['$resource', 'DateUtils'];

    function InvitacionEvento ($resource, DateUtils) {
        var resourceUrl =  'api/invitacion-eventos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.horaResolucion = DateUtils.convertDateTimeFromServer(data.horaResolucion);
                        data.horaInvitacion = DateUtils.convertDateTimeFromServer(data.horaInvitacion);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'participar': {method: 'POST', isArray: false, url: 'api/invitacion-eventos/:id/apuntarse'},
            'eventosApuntado': {method: 'GET', isArray: true, url: 'api/invitacion-eventos/eventosUsuarioApuntado'},
            'eventosNoApuntado': {method: 'GET', isArray: true, url: 'api/invitacion-eventos/eventosUsuarioNoApuntado'},
            'eventosAmigos':{method: 'GET', isArray: true, url: 'api/invitacion-eventos/eventosAmigos/:idEvento'},
            'invitacionAmigos': {method: 'POST', isArray: true, url: 'api/invitacion-eventos/invitarAmigos/:idEvento/:invitados'}
        });
    }
})();
