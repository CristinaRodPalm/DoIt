(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('EventoController', EventoController);

    EventoController.$inject = ['DataUtils', 'Evento'];

    function EventoController(DataUtils, Evento) {

        var vm = this;

        vm.eventos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Evento.query(function(result) {
                vm.eventos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
