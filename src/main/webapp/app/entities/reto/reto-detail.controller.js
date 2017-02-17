(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('RetoDetailController', RetoDetailController);

    RetoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Reto', 'ParticipacionReto', 'Evento'];

    function RetoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Reto, ParticipacionReto, Evento) {
        var vm = this;

        vm.reto = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('doitApp:retoUpdate', function(event, result) {
            vm.reto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
