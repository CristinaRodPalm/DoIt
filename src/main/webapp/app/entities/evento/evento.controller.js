(function () {
    'use strict';

    angular
        .module('doitApp')
        .controller('EventoController', EventoController);

    EventoController.$inject = ['Principal', 'DataUtils', 'Evento', 'InvitacionEvento', '$state'];

    function EventoController(Principal, DataUtils, Evento, InvitacionEvento, $state) {

        var vm = this;
        vm.eventos = [];
        vm.eventosNoApuntado = [];
        vm.eventosApuntado = [];
        vm.currentAccount;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();
        loadApuntados();
        loadNoApuntados();

        function loadAll() {
            Evento.query(function (result) {
                vm.eventos = result;
                vm.searchQuery = null;
            });
        }

        function loadApuntados() {
            InvitacionEvento.eventosApuntado(function (result) {
                vm.eventosApuntado = result;
                vm.searchQuery = null;
                console.log(vm.eventosApuntado)
            });
        }

        function loadNoApuntados() {
            InvitacionEvento.eventosNoApuntado(function (result) {
                vm.eventosNoApuntado = result;
                vm.searchQuery = null;
                console.log(vm.eventosNoApuntado)
            });
        }

        Principal.identity().then(function (account) {
            vm.currentAccount = account;
        });

        vm.apuntarse = function (id) {
            InvitacionEvento.participar({'id': id}, {});
            console.log(id);
            $state.go('eventos', null, {reload: 'eventos'});
        }
    }
})();
