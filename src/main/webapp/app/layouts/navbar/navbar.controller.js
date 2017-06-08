(function () {
    'use strict';

    angular
        .module('doitApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService', 'Amistad', 'InvitacionEvento', 'UserExt'];

    function NavbarController($state, Auth, Principal, ProfileService, LoginService, Amistad, InvitacionEvento, UserExt) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;
        vm.currentAccount;
        vm.currentUserExt;
        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;
        vm.pendingFriendRequests = [];
        vm.pendingEventInvitations = [];
        loadFriends();

        ProfileService.getProfileInfo().then(function (response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        Principal.identity().then(function (account) {
            vm.currentAccount = account;
        });

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function loadFriends(){
            Amistad.getSolicitudesAceptadas(function (result) {
                vm.accepted = result;
                console.log(result);
                vm.length = vm.accepted.length;
            });
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
            vm.currentAccount = null;
            vm.currentUserExt = null;
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }

        if (vm.isAuthenticated()) {
            getPendingFriendRequests();
            getPendingEventInvitations();
            UserExt.getUserExt(function (result) {
                vm.currentUserExt = result;
                console.log(result);
                console.log(vm.currentUserExt);
            })
        }

        function getPendingFriendRequests() {
            Amistad.getSolicitudesPendientesReceptor(function (result) {
                vm.pendingFriendRequests = result;
                console.log(result);
                console.log(vm.pendingFriendRequests);

                if (vm.pendingFriendRequests.length > 0) {
                    var badge = $("<span class='badge' style='background-color:red'>" + vm.pendingFriendRequests.length + "</span>")
                    $("#friendRequests").append(badge);
                }
            })
        }

        function getPendingEventInvitations() {
            InvitacionEvento.invitacionesPendientes(function (result) {
                vm.pendingEventInvitations = result;
                console.log(result);
                console.log(vm.pendingEventInvitations);

                if (vm.pendingEventInvitations.length > 0) {
                    var badge = $("<span class='badge' style='background-color:red'>" + vm.pendingEventInvitations.length + "</span>")
                    $("#pendingInvitations").append(badge);
                }
            })
        }
    }
})();
