(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$rootScope', '$scope', 'Principal', 'Auth','LoginService', '$state'];

    function HomeController ($rootScope, $scope, Principal, Auth, LoginService, $state) {
        var vm = this;

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
