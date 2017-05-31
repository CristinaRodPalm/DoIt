(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$rootScope', '$scope', 'Principal', 'Auth','LoginService', '$state', 'UserExt', 'Amistad', 'InvitacionEvento'];

    function HomeController ($rootScope, $scope, Principal, Auth, LoginService, $state, UserExt, Amistad, InvitacionEvento) {
        var vm = this;

        vm.user = null;
        vm.userExts = null;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = login;
        vm.register = register;
        vm.requestResetPassword = requestResetPassword;

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
            UserExt.query(function(response){
               vm.userExts = response;
               checkUser();
            });
        }

        function checkUser(){
            for(var i = 0; i < vm.userExts.length; i++){
                if(vm.userExts[i].user.id == vm.account.id){
                    vm.user = vm.userExts[i];
                }
            }
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
    }
})();
