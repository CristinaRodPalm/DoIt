(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('InvitacionEventoController', InvitacionEventoController);

    InvitacionEventoController.$inject = ['InvitacionEvento', 'Amistad'];

    function InvitacionEventoController(InvitacionEvento, Amistad) {

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
                console.log(vm.pendingInvitations);
            })
        }
    }
})();
