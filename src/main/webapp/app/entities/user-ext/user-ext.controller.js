(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('UserExtController', UserExtController);

    UserExtController.$inject = ['Principal','DataUtils', 'UserExt', 'Amistad', '$state'];

    function UserExtController(Principal, DataUtils, UserExt, Amistad, $state) {

        var vm = this;

        vm.userExts = [];
        vm.allUsers = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.amigos = null;
        vm.currentId = null;
        vm.solicitudes = [];

        loadAll();
        loadAllUsersExceptCurrent();

        function loadAll() {
            Amistad.getAmigos(function (result) {
                vm.friends = result;
            });

            UserExt.query(function(result) {
                vm.userExts = result;
                vm.searchQuery = null;
            });
        }
        function loadAllUsersExceptCurrent() {
            UserExt.allUsers(function(result) {
                vm.allUsers = result;
                vm.searchQuery = null;
            });
        }

        vm.sendFriendRequest=function(id){
            Amistad.sendFriendRequest({'id': id}, {});
            $state.go('user-search', null, {reload:'user-search'});
        }

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
            vm.currentId = vm.currentAccount.id;
        });

    }
})();
