(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('RegisterController', RegisterController);


    RegisterController.$inject = ['$scope', '$translate', '$timeout', 'Auth', 'LoginService', 'DataUtils'];

    function RegisterController ($scope, $translate, $timeout, Auth, LoginService, DataUtils) {
        var vm = this;

        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.registerAccount = {};
        vm.success = null;

        $timeout(function (){angular.element('#login').focus();});

        vm.datePickerOpenStatus.fechaEvento = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function register () {
            if (vm.registerAccount.password !== vm.confirmPassword) {
                vm.doNotMatch = 'ERROR';
            } else {
                vm.registerAccount.langKey = $translate.use();
                vm.doNotMatch = null;
                vm.error = null;
                vm.errorUserExists = null;
                vm.errorEmailExists = null;

                Auth.createAccount(vm.registerAccount).then(function () {
                        vm.success = 'OK';
                }).catch(function (response) {
                    vm.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        vm.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        vm.errorEmailExists = 'ERROR';
                    } else {
                        vm.error = 'ERROR';
                    }
                });
            }
        }

        vm.setImagen = function ($file, registerAccount) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        registerAccount.imagen = base64Data;
                        registerAccount.imagenContentType = $file.type;
                    });
                });
            }
        };
    }
})();
