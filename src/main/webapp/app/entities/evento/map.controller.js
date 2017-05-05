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



        $scope.mapStyle = [{
            "featureType": "water", "elementType": "all", "stylers": [{"hue": "#7fc8ed"},
                {"saturation": 55}, {"lightness": -6}, {"visibility": "on"}]
            },
            {
                "featureType": "water", "elementType": "labels", "stylers": [{"hue": "#7fc8ed"}, {"saturation": 55},
                {"lightness": -6}, {"visibility": "off"}]
            },
            {
                "featureType": "poi.park",
                "elementType": "geometry",
                "stylers": [{"hue": "#83cead"}, {"saturation": 1}, {"lightness": -15}, {"visibility": "on"}]
            }, {
                "featureType": "landscape", "elementType": "geometry", "stylers": [{"hue": "#f3f4f4"},
                    {"saturation": -84}, {"lightness": 59}, {"visibility": "on"}]
            },
            {
                "featureType": "landscape",
                "elementType": "labels",
                "stylers": [{"hue": "#ffffff"}, {"saturation": -100}, {"lightness": 100},
                    {"visibility": "off"}]
            }, {
                "featureType": "road", "elementType": "geometry", "stylers": [{"hue": "#ffffff"}, {"saturation": -100},
                    {"lightness": 100}, {"visibility": "on"}]
            }, {
                "featureType": "road", "elementType": "labels", "stylers": [{"hue": "#bbbbbb"},
                    {"saturation": -100}, {"lightness": 26}, {"visibility": "on"}]
            }, {
                "featureType": "road.arterial",
                "elementType": "geometry",
                "stylers": [{"hue": "#ffcc00"}, {"saturation": 100}, {"lightness": -35}, {"visibility": "simplified"}]
            }, {
                "featureType": "road.highway",
                "elementType": "geometry",
                "stylers": [{"hue": "#ffcc00"}, {"saturation": 100}, {"lightness": -22}, {"visibility": "on"}]
            }, {
                "featureType": "poi.school",
                "elementType": "all",
                "stylers": [{"hue": "#d7e4e4"}, {"saturation": -60}, {"lightness": 23}, {"visibility": "on"}]
            }];


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



        /*
         Evento.location(function (result) {
         vm.coordenadas = result;
         });*/

    }
})();
