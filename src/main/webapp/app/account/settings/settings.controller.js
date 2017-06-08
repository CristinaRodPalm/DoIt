(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('SettingsController', SettingsController);

    SettingsController.$inject = ['Principal', 'Auth', 'JhiLanguageService', '$translate', 'UserExt', 'DataUtils', '$scope', '$state'];

    function SettingsController (Principal, Auth, JhiLanguageService, $translate, UserExt, DataUtils, $scope, $state) {
        var vm = this;

        vm.error = null;
        vm.save = save;
        vm.settingsAccount = null;
        vm.success = null;
        vm.currentUserExt = [];
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;

        /**
         * Store the "settings account" in a separate variable, and not in the shared "account" variable.
         */
        var copyAccount = function (account) {
            return {
                activated: account.activated,
                email: account.email,
                firstName: account.firstName,
                langKey: account.langKey,
                lastName: account.lastName,
                login: account.login,
                imagen: account.imagen
            };

        };

        Principal.identity().then(function(account) {
            vm.settingsAccount = copyAccount(account);
            // Cargamos el userExt relacionado con el user
            UserExt.getUserExt(function(result){
                vm.currentUserExt = result;
                console.log(result);
                vm.settingsAccount.imagen = vm.currentUserExt.imagen;
                vm.settingsAccount.phone = vm.currentUserExt.telefono;
                vm.settingsAccount.nacimiento = vm.currentUserExt.nacimiento;
                vm.settingsAccount.imagenContentType  = vm.currentUserExt.imagenContentType;
            });
        });

        function save () {
            console.log(vm.settingsAccount);
            Auth.updateAccount(vm.settingsAccount).then(function() {
                vm.error = null;
                vm.success = 'OK';
                Principal.identity(true).then(function(account) {
                    vm.settingsAccount = copyAccount(account);
                    UserExt.getUserExt(function(result){
                        vm.currentUserExt = result;
                        console.log(result);
                        vm.settingsAccount.imagen = vm.currentUserExt.imagen;
                        vm.settingsAccount.phone = vm.currentUserExt.telefono;
                        vm.settingsAccount.nacimiento = vm.currentUserExt.nacimiento;
                        vm.settingsAccount.imagenContentType  = vm.currentUserExt.imagenContentType;
                    });
                });
                $state.go('settings', null, {reload: 'settings'});
                JhiLanguageService.getCurrent().then(function(current) {
                    if (vm.settingsAccount.langKey !== current) {
                        $translate.use(vm.settingsAccount.langKey);
                    }
                });
            }).catch(function() {
                vm.success = null;
                vm.error = 'ERROR';
            });
        }

        vm.setImagen = function ($file, settingsAccount) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        settingsAccount.imagen = base64Data;
                        settingsAccount.imagenContentType = $file.type;
                        console.log(settingsAccount.imagen = base64Data);
                        console.log(settingsAccount.imagenContentType = $file.type);
                    });
                });
            }
        };

        vm.datePickerOpenStatus.nacimiento = false;

        function openCalendar (date) {
            console.log("open");
            vm.datePickerOpenStatus[date] = true;
            console.log(vm.datePickerOpenStatus);
        }

    }
})();
