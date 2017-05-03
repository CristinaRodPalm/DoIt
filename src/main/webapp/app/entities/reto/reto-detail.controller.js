(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('RetoDetailController', RetoDetailController);

    RetoDetailController.$inject = ['Principal', '$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Reto', 'ParticipacionReto', 'Evento'];

    function RetoDetailController(Principal, $scope, $rootScope, $stateParams, previousState, DataUtils, entity, Reto, ParticipacionReto, Evento) {
        var vm = this;

        vm.reto = entity;
        vm.currentAccount;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;


        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });
        var unsubscribe = $rootScope.$on('doitApp:retoUpdate', function(event, result) {
            vm.reto = result;
        });

        $scope.$on('$destroy', unsubscribe);
    }
})();
