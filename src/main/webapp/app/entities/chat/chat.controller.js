(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('ChatController', ChatController);

    ChatController.$inject = ['Chat'];

    function ChatController(Chat) {

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
