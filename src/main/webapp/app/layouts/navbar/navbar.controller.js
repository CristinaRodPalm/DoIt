(function () {
    'use strict';

    angular
        .module('doitApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService', 'Amistad', 'InvitacionEvento'];

    function NavbarController($state, Auth, Principal, ProfileService, LoginService, Amistad, InvitacionEvento) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;
        vm.currentAccount;
        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;
        vm.pendingFriendRequests = [];
        vm.pendingEventInvitations = [];

        ProfileService.getProfileInfo().then(function (response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }

        if (vm.isAuthenticated) {
            Principal.identity().then(function (account) {
                vm.currentAccount = account;
            });
            getPendingFriendRequests();
            getPendingEventInvitations();
        }

        function getPendingFriendRequests() {
            Amistad.getSolicitudesPendientesReceptor(function (result) {
                vm.pendingFriendRequests = result;

                if (vm.pendingFriendRequests.length > 0) {
                    var badge = $("<span class='badge' style='background-color:red'>" + vm.pendingFriendRequests.length + "</span>")
                    $("#friendRequests").append(badge);
                }
            })
        }

        function getPendingEventInvitations() {

        }
    }
})();
