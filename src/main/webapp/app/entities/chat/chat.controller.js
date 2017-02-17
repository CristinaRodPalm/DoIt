(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('ChatController', ChatController);

    ChatController.$inject = ['$scope', '$state', 'Chat'];

    function ChatController ($scope, $state, Chat) {
        var vm = this;

        vm.chats = [];

        loadAll();

        function loadAll() {
            Chat.query(function(result) {
                vm.chats = result;
                vm.searchQuery = null;
            });
        }
    }
})();
