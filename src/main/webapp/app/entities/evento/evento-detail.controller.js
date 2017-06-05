(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('EventoDetailController', EventoDetailController);

    EventoDetailController.$inject = ['Principal', '$scope', '$rootScope', '$stateParams', 'previousState','DataUtils', 'entity', 'Evento', 'Reto', 'User', 'InvitacionEvento', 'Chat', 'Amistad'];

    function EventoDetailController(Principal, $scope, $rootScope, $stateParams, previousState, DataUtils, entity, Evento, Reto, User, InvitacionEvento, Chat, Amistad) {
        var vm = this;

        vm.evento = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.currentAccount;
        vm.friends = [];
        vm.amigosInvitados = [];
        vm.idInvitados = [];

        loadFriends();

        function loadFriends(){
            Evento.getNotInvited(function(result){
                vm.friends = result;
            });
        }

        vm.inviteFriends = function(){
            vm.idInvitados = [];
            for(var i = 0; i < vm.amigosInvitados.length; i++){
                vm.idInvitados.push(vm.amigosInvitados[i].id.toString());

            }
            console.log(vm.idInvitados);
            console.log(vm.evento.id);
            InvitacionEvento.invitacionAmigos({'idEvento':vm.evento.id, 'invitados':vm.idInvitados},{});
        }

        var unsubscribe = $rootScope.$on('doitApp:eventoUpdate', function(event, result) {
            vm.evento = result;
        });

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });

        $scope.$on('$destroy', unsubscribe);
    }
})();
