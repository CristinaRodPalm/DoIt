(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$rootScope', '$scope', 'Principal', 'Auth','LoginService', '$state', 'UserExt', 'Reto'];

    function HomeController ($rootScope, $scope, Principal, Auth, LoginService, $state, UserExt, Reto) {
        var vm = this;

        vm.userExts = null;
        vm.currentAccount;
        vm.currentUserExt;
        vm.isAuthenticated = null;
        vm.login = login;
        vm.retos = [];
        vm.register = register;
        vm.requestResetPassword = requestResetPassword;

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        getRetos();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.currentAccount = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
            UserExt.getUserExt(function (result) {
                vm.currentUserExt = result;
            })
        }

        function register () {
            $state.go('register');
        }

        function login(event){
            event.preventDefault();
            Auth.login({
                username: vm.username,
                password: vm.password,
                rememberMe: vm.rememberMe
            }).then(function () {
                vm.authenticationError = false;
                vm.isAuthenticated = Principal.isAuthenticated;
                $rootScope.$broadcast('authenticationSuccess');
                if (Auth.getPreviousState()) {
                    var previousState = Auth.getPreviousState();
                    Auth.resetPreviousState();
                    $state.go(previousState.name, previousState.params);
                }
            }).catch(function () {
                vm.authenticationError = true;
            });
        }

        function requestResetPassword () {
            $state.go('requestReset');
        }

        function getRetos(){
            Reto.query(function (result) {
                vm.retos = result;
            })
        }
    }
})();
