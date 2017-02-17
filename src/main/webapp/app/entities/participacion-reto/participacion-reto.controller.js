(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('ParticipacionRetoController', ParticipacionRetoController);

    ParticipacionRetoController.$inject = ['$scope', '$state', 'DataUtils', 'ParticipacionReto'];

    function ParticipacionRetoController ($scope, $state, DataUtils, ParticipacionReto) {
        var vm = this;

        vm.participacionRetos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            ParticipacionReto.query(function(result) {
                vm.participacionRetos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
