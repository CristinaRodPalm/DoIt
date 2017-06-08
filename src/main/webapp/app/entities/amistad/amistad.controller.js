(function () {
    'use strict';

    angular
        .module('doitApp')
        .controller('AmistadController', AmistadController);

    AmistadController.$inject = ['Amistad', '$state', 'Principal'];

    function AmistadController(Amistad, $state, Principal) {

        var vm = this;

        vm.amistads = [];
        vm.amistadsCurrentUser = [];

        vm.otherUsers = [];
        vm.pendingEmisor = [];
        vm.pendingReceptor = [];
        vm.accepted = [];

        loadFriends();
        loadNonRelated();
        loadRecieved();
        loadSended();
        loadAmistadsCurrentUser();

        function loadAmistadsCurrentUser() {
            Amistad.getAllByCurrentUser(function (result) {
                vm.amistadsCurrentUser = result;
                console.log(vm.amistadsCurrentUser);
                console.log(result);
            });
        }

        vm.acceptFriend = function(id){
            Amistad.accept({'id':id}, {});
            $state.reload();
        }

        vm.denyFriend = function(id){
            Amistad.deny({'id':id}, {});
            $state.reload();
        }

        //users amigos al actual
        function loadFriends(){
            Amistad.getSolicitudesAceptadas(function (result) {
                vm.accepted = result;
                console.log(vm.accepted);
                console.log(result);
            });
        }

        //solicitud pendiente --> eres el emisor
        function loadSended(){
            Amistad.getSolicitudesPendientesEmisor(function(result){
                vm.pendingEmisor = result;
                console.log(result);
                console.log(vm.pendingEmisor);
            });
        }

        //solicitud pendiente --> eres el receptor
        function loadRecieved(){
            Amistad.getSolicitudesPendientesReceptor(function(result){
                vm.pendingReceptor = result;
                console.log(result);
                console.log(vm.pendingReceptor);
            });
        }

        // resto de usuarios
        function loadNonRelated(){
            Amistad.getNonRelatedUsers(function (result){
                vm.otherUsers = result;
            });
        }

        vm.sendFriendRequest = function(id){
            Amistad.sendFriendRequest({'id': id}, {});
            $state.reload();
        }

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
            vm.currentId = vm.currentAccount.id;
        });
    }
})();
