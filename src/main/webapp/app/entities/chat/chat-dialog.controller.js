(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('ChatDialogController', ChatDialogController);

    ChatDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Chat', 'Evento', 'Mensaje'];

    function ChatDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Chat, Evento, Mensaje) {
        var vm = this;

        vm.chat = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.eventos = Evento.query();
        vm.mensajes = Mensaje.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.chat.id !== null) {
                Chat.update(vm.chat, onSaveSuccess, onSaveError);
            } else {
                Chat.save(vm.chat, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('doitApp:chatUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.horaCreacion = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
