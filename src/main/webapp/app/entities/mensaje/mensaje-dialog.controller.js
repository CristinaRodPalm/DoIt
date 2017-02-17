(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('MensajeDialogController', MensajeDialogController);

    MensajeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Mensaje', 'User', 'Chat'];

    function MensajeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Mensaje, User, Chat) {
        var vm = this;

        vm.mensaje = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.users = User.query();
        vm.chats = Chat.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mensaje.id !== null) {
                Mensaje.update(vm.mensaje, onSaveSuccess, onSaveError);
            } else {
                Mensaje.save(vm.mensaje, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('doitApp:mensajeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.horaEnvio = false;

        vm.setFoto = function ($file, mensaje) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        mensaje.foto = base64Data;
                        mensaje.fotoContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
