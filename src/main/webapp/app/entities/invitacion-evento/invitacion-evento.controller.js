(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('InvitacionEventoController', InvitacionEventoController);

    InvitacionEventoController.$inject = ['InvitacionEvento', '$state'];

    function InvitacionEventoController(InvitacionEvento, $state) {

        var vm = this;

        vm.invitacionEventos = [];
        vm.pendingInvitations = [];

        loadAll();
        loadPendingInvitations();

        function loadAll() {
            InvitacionEvento.query(function(result) {
                vm.invitacionEventos = result;
                vm.searchQuery = null;
            });
        }
        function loadPendingInvitations(){
            InvitacionEvento.invitacionesPendientes(function (result) {
                vm.pendingInvitations = result;
            })
        }

        vm.acceptInvitation = function(id){
            InvitacionEvento.accept({'id':id}, {});
            $state.go('invitaciones-pendientes', null, {reload: 'invitaciones-pendientes'});
        }

        vm.denyInvitation = function(id){
            InvitacionEvento.deny({'id':id}, {});
            $state.go('invitaciones-pendientes', null, {reload: 'invitaciones-pendientes'});
        }
    }
})();
