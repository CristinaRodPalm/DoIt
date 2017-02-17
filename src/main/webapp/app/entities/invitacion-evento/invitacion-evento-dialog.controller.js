(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('InvitacionEventoDialogController', InvitacionEventoDialogController);

    InvitacionEventoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'InvitacionEvento', 'Evento', 'User'];

    function InvitacionEventoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, InvitacionEvento, Evento, User) {
        var vm = this;

        vm.invitacionEvento = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.eventos = Evento.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.invitacionEvento.id !== null) {
                InvitacionEvento.update(vm.invitacionEvento, onSaveSuccess, onSaveError);
            } else {
                InvitacionEvento.save(vm.invitacionEvento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('doitApp:invitacionEventoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.horaResolucion = false;
        vm.datePickerOpenStatus.horaInvitacion = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
