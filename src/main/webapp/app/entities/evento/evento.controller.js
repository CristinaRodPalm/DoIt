(function() {
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


        function loadAll() {
            Evento.query(function (result) {
                vm.eventos = result;
                vm.searchQuery = null;
            });

            InvitacionEvento.eventosApuntados(function (result) {
                vm.eventoApuntado = result;
                vm.searchQuery = null;
                console.log(vm.eventoApuntado);
                eventosNoApuntados();
            });

            InvitacionEvento.eventosNoApuntados(function (result) {
                vm.eventoNoApuntado = result;
                vm.searchQuery = null;
                console.log(vm.eventoNoApuntado);
            })
        }

        function eventosNoApuntados() {
            // QUITAMOS LOS EVENTOS QUE YA ESTA APUNTADO
            for (var i = 0; i < vm.eventos.length; i++) {
                if (vm.eventoApuntado.length > 0) {
                    for (var j = 0; j < vm.eventoApuntado.length; j++) {
                        if (vm.eventos[i].id == vm.eventoApuntado[j].evento.id) {
                            vm.eventos.splice(i, 1);
                        }
                    }
                }
            }
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
