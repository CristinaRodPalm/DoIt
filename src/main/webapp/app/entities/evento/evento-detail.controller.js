(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('EventoDetailController', EventoDetailController);

    EventoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Evento', 'Reto', 'User', 'InvitacionEvento', 'Chat'];

    function EventoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Evento, Reto, User, InvitacionEvento, Chat) {
        var vm = this;

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

        vm.evento = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
/*
        Evento.getOne(function (result){
            console.log(result);
            vm.coordenadas = result;
        });
*/
        var unsubscribe = $rootScope.$on('doitApp:eventoUpdate', function(event, result) {
            vm.evento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
