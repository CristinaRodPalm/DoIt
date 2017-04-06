(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('RetoController', RetoController);

    RetoController.$inject = ['DataUtils', 'Reto'];

    function RetoController(DataUtils, Reto) {

        var vm = this;

        vm.retos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Reto.query(function(result) {
                vm.retos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
