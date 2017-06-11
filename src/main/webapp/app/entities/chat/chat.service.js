(function() {
    'use strict';
    angular
        .module('doitApp')
        .factory('Chat', Chat);

    Chat.$inject = ['$resource', 'DateUtils'];

    function Chat ($resource, DateUtils) {
        var resourceUrl =  'api/chats/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.horaCreacion = DateUtils.convertDateTimeFromServer(data.horaCreacion);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'create': {method:'POST', url:'/api/chatByID/:idReceptor'}
        });
    }
})();
