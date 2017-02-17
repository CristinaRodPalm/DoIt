(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('MensajeController', MensajeController);

    MensajeController.$inject = ['$scope', '$state', 'DataUtils', 'Mensaje'];

    function MensajeController ($scope, $state, DataUtils, Mensaje) {
        var vm = this;

        vm.mensajes = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Mensaje.query(function(result) {
                vm.mensajes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
