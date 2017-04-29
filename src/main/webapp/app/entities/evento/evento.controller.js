(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('EventoController', EventoController);

    EventoController.$inject = ['Principal', 'DataUtils', 'Evento'];

    function EventoController(Principal, DataUtils, Evento) {

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

    }
})();
