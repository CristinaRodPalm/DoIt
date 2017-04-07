(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('EventoDialogController', EventoDialogController)
        .config(['uiGmapGoogleMapApiProvider', function (GoogleMapApi) {
            GoogleMapApi.configure({
                key: 'AIzaSyA9Errugk2Ao7N8dH2PVbSy_oi8rBVTe0DQ',
                v: '2.4.1',
                libraries: 'places'
            });
        }]);

    EventoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Evento', 'Reto', 'User', 'InvitacionEvento', 'Chat'];

    function EventoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Evento, Reto, User, InvitacionEvento, Chat) {
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


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
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
            $uibModalInstance.close(result);
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


        // AUTOCOMPLETE
        angular.extend($scope, {

            searchbox: {
                template:'searchbox.tpl.html',
                events:{
                    places_changed: function (searchBox) {}
                }
            },
            options: {
                scrollwheel: false
            }
        });

        GoogleMapApi.then(function(maps) {
            maps.visualRefresh = true;
        });


    }
})();
