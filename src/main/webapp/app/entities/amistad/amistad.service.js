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
            'getAmigos': {method:'GET', isArray:true, url:'api/amigosUserExt'},
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
            'accept': {
                method: 'PUT',
                url: 'api/amistads/:id/accept'
            },
            'deny': {
                method: 'PUT',
                url: 'api/amistads/:id/deny'

            },
            'getFriends':{method: 'GET', isArray: true, url: 'api/amigos'}

        });
    }
})();
