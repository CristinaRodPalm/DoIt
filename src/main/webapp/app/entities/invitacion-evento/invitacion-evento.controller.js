(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('InvitacionEventoController', InvitacionEventoController);

    InvitacionEventoController.$inject = ['InvitacionEvento'];

    function InvitacionEventoController(InvitacionEvento) {

        var vm = this;

        vm.invitacionEventos = [];

        loadAll();

        function loadAll() {
            InvitacionEvento.query(function(result) {
                vm.invitacionEventos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
