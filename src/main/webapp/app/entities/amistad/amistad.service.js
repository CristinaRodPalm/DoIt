(function () {
    'use strict';
    angular
        .module('doitApp')
        .factory('Amistad', Amistad);

    Amistad.$inject = ['$resource', 'DateUtils'];

    function Amistad($resource, DateUtils) {
        var resourceUrl = 'api/amistads/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'getAllByCurrentUser': {method: 'GET', isArray: true, url: 'api/amistades'},
            'getSolicitudesAceptadas': {method:'GET', isArray:true, url:'api/usersSolAceptadas'},
            'getSolicitudesPendientes': {method:'GET', isArray:true, url:'api/usersSolPendientes'},
            'getSolicitudesPendientesEmisor': {method:'GET', isArray:true, url:'api/usersSolPendientesEmisor'},
            'getSolicitudesPendientesReceptor': {method:'GET', isArray:true, url:'api/usersSolPendientesReceptor'},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.timeStamp = DateUtils.convertDateTimeFromServer(data.timeStamp);
                        data.horaRespuesta = DateUtils.convertDateTimeFromServer(data.horaRespuesta);
                    }
                    return data;
                }
            },
            'update': {method: 'PUT'},

            'sendFriendRequest': {method: 'POST', isArray: false, url: 'api/amistad/:id/emisor'},

            'accept': { method: 'PUT', url: 'api/amistads/:id/accept' },
            'deny': { method: 'PUT', url: 'api/amistads/:id/deny' },

            'acceptByUser': {method:'PUT', url:'api/amistads/:id/acceptByUser'},
            'getFriends':{method: 'GET', isArray: true, url: 'api/amigos'},
            'getOneByUsers':{method: 'GET', isArray:false, url:'api/amistadByUsers/:id'}

        });
    }
})();
