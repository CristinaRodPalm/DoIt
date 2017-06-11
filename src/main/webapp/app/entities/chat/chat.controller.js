(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('ChatController', ChatController);

    ChatController.$inject = ['Chat', 'UserExt', 'Principal'];

    function ChatController(Chat, UserExt, Principal) {

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
            console.log(idReceptor);
            Chat.create({'idReceptor':idReceptor}, {});
        }

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });
    }
})();
