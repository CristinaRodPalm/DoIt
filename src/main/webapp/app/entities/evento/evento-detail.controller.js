(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('EventoDetailController', EventoDetailController);

    EventoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Evento', 'Reto', 'User', 'InvitacionEvento', 'Chat'];

    function EventoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Evento, Reto, User, InvitacionEvento, Chat) {
        var vm = this;

        vm.evento = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        Evento.get(function (result){
            console.log(result);
            vm.coordenadas = result;
        });

        var unsubscribe = $rootScope.$on('doitApp:eventoUpdate', function(event, result) {
            vm.evento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
