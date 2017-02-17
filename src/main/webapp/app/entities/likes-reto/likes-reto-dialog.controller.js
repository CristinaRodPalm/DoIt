(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('LikesRetoDialogController', LikesRetoDialogController);

    LikesRetoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LikesReto', 'User', 'ParticipacionReto'];

    function LikesRetoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LikesReto, User, ParticipacionReto) {
        var vm = this;

        vm.likesReto = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.participacionretos = ParticipacionReto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.likesReto.id !== null) {
                LikesReto.update(vm.likesReto, onSaveSuccess, onSaveError);
            } else {
                LikesReto.save(vm.likesReto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('doitApp:likesRetoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.horaLike = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
