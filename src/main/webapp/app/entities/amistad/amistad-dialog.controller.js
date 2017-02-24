(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('AmistadDialogController', AmistadDialogController);

    AmistadDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Amistad', 'User'];

    function AmistadDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Amistad, User) {
        var vm = this;

        vm.amistad = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.amistad.id !== null) {
                Amistad.update(vm.amistad, onSaveSuccess, onSaveError);
            } else {
                Amistad.save(vm.amistad, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('doitApp:amistadUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.timeStamp = false;
        vm.datePickerOpenStatus.horaRespuesta = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
