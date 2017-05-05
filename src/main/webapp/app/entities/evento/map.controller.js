(function () {
    'use strict';

    angular
        .module('doitApp')
        .controller('mapController', mapController);

    mapController.$inject = ['NgMap', '$scope', '$state', 'DataUtils', 'Evento'];

    function mapController(NgMap, $scope, $state, DataUtils, Evento) {
        var vm = this;

        $scope.map = null;
        $scope.mapa;

        vm.placeChanged = function() {
            vm.place = this.getPlace();
            console.log('location', vm.place.geometry.location);
            vm.map.setCenter(vm.place.geometry.location);
            vm.evento.latitud = vm.place.geometry.location.lat();
            vm.evento.longitud = vm.place.geometry.location.lng();
            vm.map.setZoom(8);
        }
        NgMap.getMap().then(function(map) {
            vm.map = map;
            vm.map.setZoom(2);
            vm.map.setCenter(new google.maps.LatLng(32, -10));
            vm.map.setOptions({maxZoom:15, minZoom: 2});
        });

        Evento.query(function (result) {
            vm.eventos = result;
        });
    }
})();
