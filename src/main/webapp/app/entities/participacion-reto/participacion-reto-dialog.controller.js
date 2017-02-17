(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('ParticipacionRetoDialogController', ParticipacionRetoDialogController);

    ParticipacionRetoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'ParticipacionReto', 'User', 'Reto', 'LikesReto'];

    function ParticipacionRetoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, ParticipacionReto, User, Reto, LikesReto) {
        var vm = this;

        vm.participacionReto = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.users = User.query();
        vm.retos = Reto.query();
        vm.likesretos = LikesReto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.participacionReto.id !== null) {
                ParticipacionReto.update(vm.participacionReto, onSaveSuccess, onSaveError);
            } else {
                ParticipacionReto.save(vm.participacionReto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('doitApp:participacionRetoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImagen = function ($file, participacionReto) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        participacionReto.imagen = base64Data;
                        participacionReto.imagenContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.horaPublicacion = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
