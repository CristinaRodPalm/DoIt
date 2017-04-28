(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('RetoDialogController', RetoDialogController);

    RetoDialogController.$inject = ['$timeout', '$scope', '$state', '$stateParams', 'DataUtils', 'entity', 'Reto', 'ParticipacionReto', 'Evento'];

    function RetoDialogController ($timeout, $scope, $state, $stateParams, DataUtils, entity, Reto, ParticipacionReto, Evento) {
        var vm = this;

        vm.reto = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.participacionretos = ParticipacionReto.query();
        vm.eventos = Evento.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $state.go('retos', null, {reload: 'retos'});
        }

        function save () {
            vm.isSaving = true;
            if (vm.reto.id !== null) {
                Reto.update(vm.reto, onSaveSuccess, onSaveError);
            } else {
                Reto.save(vm.reto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('doitApp:retoUpdate', result);
            vm.isSaving = false;
            $state.go('lista-retos', null, {reload: 'lista-retos'});
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.horaPublicacion = false;

        vm.setImagen = function ($file, reto) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        reto.imagen = base64Data;
                        reto.imagenContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
