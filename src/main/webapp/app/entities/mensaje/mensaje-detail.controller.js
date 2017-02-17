(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('MensajeDetailController', MensajeDetailController);

    MensajeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Mensaje', 'User', 'Chat'];

    function MensajeDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Mensaje, User, Chat) {
        var vm = this;

        vm.mensaje = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('doitApp:mensajeUpdate', function(event, result) {
            vm.mensaje = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
