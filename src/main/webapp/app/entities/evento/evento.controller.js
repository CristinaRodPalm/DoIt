(function () {
    'use strict';

    angular
        .module('doitApp')
        .controller('EventoController', EventoController);

    EventoController.$inject = ['$scope', '$state', 'DataUtils', 'Evento'];

    function EventoController($scope, $state, DataUtils, Evento) {
        var vm = this;

        //vm.apiMapsUrl = "AIzaSyA9Errugk2Ao7N8dH2PVSy_oi8rBVTe0DQ";
        vm.eventos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();
        map();

        function loadAll() {
            Evento.query(function (result) {
                vm.eventos = result;
                vm.searchQuery = null;
            });
        }

        function map(ngMap) {
            ngMap.getMap().then(function (map) {
                console.log(map.getCenter());
                console.log('markers', map.markers);
                console.log('shapes', map.shapes);
            });
        }
    }
})();
