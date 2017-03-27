(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('EventoController', EventoController);

    EventoController.$inject = ['$scope', '$state', 'DataUtils', 'Evento'];

    function EventoController ($scope, $state, DataUtils, Evento) {
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
