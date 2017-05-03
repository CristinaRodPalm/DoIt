(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('UserExtController', UserExtController);

    UserExtController.$inject = ['Principal','DataUtils', 'UserExt', 'Amistad', '$state'];

    function UserExtController(Principal, DataUtils, UserExt, Amistad, $state) {

        var vm = this;

        vm.userExts = [];
        vm.allUsers = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.amigos = null;
        vm.currentId = null;
        vm.solicitudes = [];
        vm.otherUsers = [];

        loadAll();
        loadAllUsersExceptCurrent();

        function loadAll() {
            // users amigos al actual
            Amistad.getSolicitudesAceptadas(function (result) {
                vm.accepted = result;
            });

            // users con solicitud pendiente
            Amistad.getSolicitudesPendientes(function(result){
               vm.pending = result;
            });

            // resto de usuarios que no son amigos ni tienen solicitud
            UserExt.query(function(result) {
                vm.userExts = result;
                vm.searchQuery = null;
                test();
            });
        }
        function loadAllUsersExceptCurrent() {
            UserExt.allUsers(function(result) {
                vm.allUsers = result;
                vm.searchQuery = null;
            });
        }

        vm.sendFriendRequest=function(id){
            Amistad.sendFriendRequest({'id': id}, {});
            $state.go('user-search', null, {reload:'user-search'});
        }

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
            vm.currentId = vm.currentAccount.id;
        });

        function test() {
            vm.otherUsers = vm.userExts;
            // vm.otherUsers --> array de amigos sin solicitud pendiente ni agregados
            for(var i = 0; i < vm.userExts.length; i++){
                // quitamos los que ya tenemos agregados
                for(var j = 0; j < vm.accepted.length; j++){
                    if(vm.userExts[i].user.id == vm.accepted[j].user.id){
                        vm.otherUsers.splice(i, 1);
                    }
                }
            }
            for(var i = 0; i < vm.userExts.length; i++){
                // quitamos los de solicitud pendiente
                for(var j = 0; j < vm.pending.length; j++){
                    if(vm.userExts[i].user.id == vm.pending[j].user.id){
                        vm.otherUsers.splice(i, 1);
                    }
                }
            }
        }
    }
})();
