(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('ChatController', ChatController);

    ChatController.$inject = ['Chat', 'UserExt', 'Principal', '$state'];

    function ChatController(Chat, UserExt, Principal, $state) {

        var vm = this;
        vm.idReceptor = null;

        vm.chats = [];

        loadAll();

        function loadAll() {
            Chat.query(function(result) {
                vm.chats = result;
                vm.searchQuery = null;
            });
            vm.users = UserExt.query();
        }

        vm.crearChat = function(idReceptor){
            Chat.create({'idReceptor':idReceptor}, {}).$promise.then(function (response) {
                $state.go('chat-list', {'chat': response});
            });
        }

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });
    }
})();
