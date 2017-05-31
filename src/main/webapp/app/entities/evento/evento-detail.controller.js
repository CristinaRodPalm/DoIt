(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('EventoDetailController', EventoDetailController);

    EventoDetailController.$inject = ['Principal', '$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Evento', 'Reto', 'User', 'InvitacionEvento', 'Chat', 'Amistad'];

    function EventoDetailController(Principal, $scope, $rootScope, $stateParams, previousState, DataUtils, entity, Evento, Reto, User, InvitacionEvento, Chat, Amistad) {
        var vm = this;

        vm.evento = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.currentAccount;
        vm.friends = [];
        vm.amigosInvitados = [];
        
        loadFriends();



        var unsubscribe = $rootScope.$on('doitApp:eventoUpdate', function(event, result) {
            vm.evento = result;
        });

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });

        $scope.$on('$destroy', unsubscribe);

        function loadFriends(){
            Amistad.getFriends(function (result) {
                vm.friends = result;
            })
        }

        vm.inviteFriends = function(id){
            InvitacionEvento.invitacionAmigos({'id':id}, {});
            console.log(id);
            console.log(vm.amigosInvitados);

        }
    }
})();
