(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('EventoDetailController', EventoDetailController);

    EventoDetailController.$inject = ['Principal', '$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Evento', 'Reto', 'User', 'InvitacionEvento', 'Chat'];

    function EventoDetailController(Principal, $scope, $rootScope, $stateParams, previousState, DataUtils, entity, Evento, Reto, User, InvitacionEvento, Chat) {
        var vm = this;

        vm.evento = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.currentAccount;
        var unsubscribe = $rootScope.$on('doitApp:eventoUpdate', function(event, result) {
            vm.evento = result;
        });

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });

        $scope.$on('$destroy', unsubscribe);
    }
})();
