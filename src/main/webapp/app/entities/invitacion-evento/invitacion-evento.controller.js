(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('InvitacionEventoController', InvitacionEventoController);

    InvitacionEventoController.$inject = ['$scope', '$state', 'InvitacionEvento'];

    function InvitacionEventoController ($scope, $state, InvitacionEvento) {
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
