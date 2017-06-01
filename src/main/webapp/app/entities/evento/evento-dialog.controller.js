(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('EventoDialogController', EventoDialogController);

    EventoDialogController.$inject = ['NgMap', '$timeout', '$scope', '$state','$stateParams', 'DataUtils', 'entity', 'Evento', 'Reto', 'User', 'InvitacionEvento', 'Chat', 'Amistad'];

    function EventoDialogController (NgMap, $timeout, $scope, $state, $stateParams, DataUtils, entity, Evento, Reto, User, InvitacionEvento, Chat, Amistad) {
        var vm = this;

        vm.evento = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.retos = Reto.query();
        vm.users = User.query();
        vm.invitacioneventos = InvitacionEvento.query();
        vm.chats = Chat.query();
        vm.friends = [];
        vm.inviteFriends = [];

        loadFriends();

        vm.placeChanged = function() {
            vm.place = this.getPlace();
            console.log('location', vm.place.geometry.location);
            vm.map.setCenter(vm.place.geometry.location);
            vm.evento.latitud = vm.place.geometry.location.lat();
            vm.evento.longitud = vm.place.geometry.location.lng();
            vm.map.setZoom(15);
        }
        NgMap.getMap().then(function(map) {
            vm.map = map;
            vm.map.setZoom(6);
            vm.map.setCenter(new google.maps.LatLng(40.4378698, -3.8196217));
        });


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {

            //$uibModalInstance.close();
            $state.go('eventos', null, {reload: 'evento'});
        }

        function save () {
            vm.isSaving = true;
            if (vm.evento.id !== null) {
                Evento.update(vm.evento, onSaveSuccess, onSaveError);
            } else {
                Evento.save(vm.evento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('doitApp:eventoUpdate', result);
            $state.go('eventos', null, {reload: 'evento'});
            //$uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.hora = false;

        vm.setImagen = function ($file, evento) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        evento.imagen = base64Data;
                        evento.imagenContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.fechaEvento = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function loadFriends(){
            Amistad.getFriends(function (result) {
                vm.friends = result;
            })
        }
    }
})();
