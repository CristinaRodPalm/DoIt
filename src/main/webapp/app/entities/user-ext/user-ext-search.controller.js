(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('UserExtSearchController',UserExtSearchController);

    UserExtSearchController.$inject = ['$scope', '$state', 'DataUtils', 'UserExt'];

    function UserExtSearchController ($scope, $state, DataUtils, UserExt) {
        var vm = this;

        vm.userExts = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            UserExt.query(function(result) {
                vm.userExts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
