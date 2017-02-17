(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('ChatDetailController', ChatDetailController);

    ChatDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Chat', 'Evento', 'Mensaje'];

    function ChatDetailController($scope, $rootScope, $stateParams, previousState, entity, Chat, Evento, Mensaje) {
        var vm = this;

        vm.chat = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('doitApp:chatUpdate', function(event, result) {
            vm.chat = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
