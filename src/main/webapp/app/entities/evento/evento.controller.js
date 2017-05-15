(function () {
    'use strict';

    angular
        .module('doitApp')
        .controller('EventoController', EventoController);

    EventoController.$inject = ['Principal', 'DataUtils', 'Evento', 'InvitacionEvento', '$state'];

    function EventoController(Principal, DataUtils, Evento, InvitacionEvento, $state) {

        var vm = this;
        vm.eventos = [];
        vm.eventoNoApuntado = [];
        vm.eventoApuntado = [];
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
            InvitacionEvento.eventosApuntados(function (result) {
                vm.eventoApuntado = result;
                vm.searchQuery = null;
                console.log(vm.eventoApuntado);
                //eventosNoApuntados();
            });
        }

        function loadNoApuntados() {
            InvitacionEvento.eventosNoApuntados(function (result) {
                vm.eventoNoApuntado = result;
                vm.searchQuery = null;
                console.log(vm.eventoNoApuntado);
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
