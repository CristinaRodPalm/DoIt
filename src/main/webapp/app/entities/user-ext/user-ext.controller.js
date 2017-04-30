(function () {
    'use strict';

    angular
        .module('doitApp')
        .controller('UserExtController', UserExtController);

    UserExtController.$inject = ['Principal', 'DataUtils', 'UserExt', 'Amistad', '$state'];

    function UserExtController(Principal, DataUtils, UserExt, Amistad, $state) {

        var vm = this;

        vm.userExts = [];
        vm.allUsers = [];
        vm.currentAccount;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();
        loadAllUsersExceptCurrent();

        function loadAll() {
            UserExt.query(function (result) {
                vm.userExts = result;
                vm.searchQuery = null;
            });
        }

        function loadAllUsersExceptCurrent() {
            UserExt.allUsers(function (result) {
                vm.allUsers = result;
                vm.searchQuery = null;
            });
        }

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });

        vm.sendFriendRequest = function (id) {
            Amistad.sendFriendRequest({'id': id}, {});
            $state.go('user-search', null, {reload: 'user-search'});
        }
    }
})();
