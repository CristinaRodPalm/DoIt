(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('EventoController', EventoController);

    EventoController.$inject = ['Principal', 'DataUtils', 'Evento', 'InvitacionEvento', '$state'];

    function EventoController(Principal, DataUtils, Evento, InvitacionEvento, $state) {

        var vm = this;

        vm.eventos = [];
        vm.currentAccount;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Evento.query(function(result) {
                vm.eventos = result;
                vm.searchQuery = null;
            });
        }

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });

        vm.apuntarse = function(id){
            InvitacionEvento.participar({'id': id}, {});
            console.log(id);
            $state.go('eventos', null, {reload:'eventos'});
        }

    }
})();
