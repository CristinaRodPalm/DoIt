(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('InvitacionEventoDetailController', InvitacionEventoDetailController);

    InvitacionEventoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'InvitacionEvento', 'Evento', 'User'];

    function InvitacionEventoDetailController($scope, $rootScope, $stateParams, previousState, entity, InvitacionEvento, Evento, User) {
        var vm = this;

        vm.invitacionEvento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('doitApp:invitacionEventoUpdate', function(event, result) {
            vm.invitacionEvento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
