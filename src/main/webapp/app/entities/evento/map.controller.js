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

        $scope.mapStyle = [{"featureType":"water","elementType":"all","stylers":[{"hue":"#7fc8ed"},
            {"saturation":55},{"lightness":-6},{"visibility":"on"}]},
            {"featureType":"water","elementType":"labels","stylers":[{"hue":"#7fc8ed"},{"saturation":55},
            {"lightness":-6},{"visibility":"off"}]},
            {"featureType":"poi.park","elementType":"geometry","stylers":[{"hue":"#83cead"},{"saturation":1}, {"lightness":-15},{"visibility":"on"}]},{"featureType":"landscape","elementType":"geometry","stylers":[{"hue":"#f3f4f4"},
                {"saturation":-84},{"lightness":59},{"visibility":"on"}]},
            {"featureType":"landscape","elementType":"labels","stylers":[{"hue":"#ffffff"},{"saturation":-100},{"lightness":100},
                {"visibility":"off"}]},{"featureType":"road","elementType":"geometry","stylers":[{"hue":"#ffffff"},{"saturation":-100},
                {"lightness":100},{"visibility":"on"}]},{"featureType":"road","elementType":"labels","stylers":[{"hue":"#bbbbbb"},
                {"saturation":-100},{"lightness":26},{"visibility":"on"}]},{"featureType":"road.arterial","elementType":"geometry","stylers":[{"hue":"#ffcc00"},{"saturation":100},{"lightness":-35},{"visibility":"simplified"}]},{"featureType":"road.highway","elementType":"geometry","stylers":[{"hue":"#ffcc00"},{"saturation":100},{"lightness":-22},{"visibility":"on"}]},{"featureType":"poi.school","elementType":"all","stylers":[{"hue":"#d7e4e4"},{"saturation":-60},{"lightness":23},{"visibility":"on"}]}];

        NgMap.getMap(mapa).then(function(map) {
            console.log(map.getCenter());
            console.log('markers', map.markers);
            console.log('shapes', map.shapes);
            $scope.map = map;


        });

            Evento.query(function (result) {
                console.log(result);
                vm.posicion = result;
            });

/*
        $http({
            method: 'GET',
            url: 'api/stats/song/' + res.song.id
        }).then(function successCallback(response) {
            $scope.statsForMap = response.data;
            NgMap.getMap('playmap').then(function (map) {
                $scope.map = map;
                $scope.statsForMap.forEach(function (item) {
                    var latLng = new google.maps.LatLng(item.latitude, item.longitude);
                    $scope.dynMarkers.push(new google.maps.Marker({position: latLng}));
                });
                var markers = $scope.dynMarkers.map(function (location, i) {
                    return new google.maps.Marker({
                        position: location.position
                    });
                });
                $scope.markerClusterer = new MarkerClusterer(map, markers, {
                    styles: markerIcons
                });
                var bounds = new google.maps.LatLngBounds();
                for (var i = 0; i < markers.length; i++) {
                    bounds.extend(markers[i].getPosition());
                }
                map.fitBounds(bounds);
            });
        });*/
    }
})();
