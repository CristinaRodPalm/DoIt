(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('ChatController', ChatController);

    ChatController.$inject = ['Chat', 'UserExt', 'Principal', '$state'];

    function ChatController(Chat, UserExt, Principal, $state) {

        var vm = this;

        vm.chats = [];

        loadAll();

        function loadAll() {
            Chat.query(function(result) {
                vm.chats = result;
                vm.searchQuery = null;
            });
            vm.users = UserExt.query();
            console.log(vm.users);
        }

        vm.crearChat = function(idReceptor){
            //console.log(idReceptor);
            vm.chat = Chat.create({'idReceptor':idReceptor}, {});
            console.log(vm.chat);
            $state.go('chat-list', {'chat': vm.chat});

        }

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });
    }
})();
