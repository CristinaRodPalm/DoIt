(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('RetoController', RetoController);

    RetoController.$inject = ['DataUtils', 'Reto', 'Principal'];

    function RetoController(DataUtils, Reto, Principal) {

        var vm = this;

        vm.retos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.currentAccount;

        loadAll();

        function loadAll() {
            Reto.query(function(result) {
                vm.retos = result;
                vm.searchQuery = null;
            });
        }

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });
    }
})();
