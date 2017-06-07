(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('ParticipacionRetoController', ParticipacionRetoController);

    ParticipacionRetoController.$inject = ['DataUtils', 'ParticipacionReto'];

    function ParticipacionRetoController(DataUtils, ParticipacionReto) {

        var vm = this;

        vm.participacionRetos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            ParticipacionReto.query(function(result) {
                vm.participacionRetos = result;
                console.log(result);
                vm.searchQuery = null;
            });
        }
    }
})();
