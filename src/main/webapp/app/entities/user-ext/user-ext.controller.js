(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('UserExtController', UserExtController);

    UserExtController.$inject = ['Principal','DataUtils', 'UserExt', 'Amistad', '$state'];

    function UserExtController(Principal, DataUtils, UserExt, Amistad, $state) {

        var vm = this;

        vm.userExts = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.currentId = null;
        vm.otherUsers = [];
        vm.pendingEmisor = [];
        vm.pendingReceptor = [];
        vm.accepted = [];

        loadAll();
        loadSended();
        loadRecieved();
        loadNonRelated();
        loadFriends();

        //users amigos al actual
        function loadFriends(){
            Amistad.getSolicitudesAceptadas(function (result) {
                vm.accepted = result;
            });
        }

        //solicitud pendiente --> eres el emisor
        function loadSended(){
            Amistad.getSolicitudesPendientesEmisor(function(result){
                vm.pendingEmisor = result;
            });
        }

        //solicitud pendiente --> eres el receptor
        function loadRecieved(){
            Amistad.getSolicitudesPendientesReceptor(function(result){
                vm.pendingReceptor = result;
            });
        }

        // resto de usuarios
        function loadNonRelated(){
            Amistad.getNonRelatedUsers(function (result){
                vm.otherUsers = result;
            });
        }

        //generado por jhipster
        function loadAll() {
            UserExt.query(function(result) {
                vm.userExts = result;
                vm.searchQuery = null;
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
