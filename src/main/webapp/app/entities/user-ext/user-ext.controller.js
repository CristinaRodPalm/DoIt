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
        vm.currentId = null;
        vm.otherUsers = [];
        vm.pendingEmisor = [];
        vm.pendingReceptor = [];
        vm.pending = [];
        vm.accepted = [];

        loadAll();

        function loadAll() {
            // users amigos al actual
            Amistad.getSolicitudesAceptadas(function (result) {
                vm.accepted = result;
            });

            // users con solicitud pendiente
            Amistad.getSolicitudesPendientes(function(result){
                vm.resultado = result;
                for(var i = 0; i < vm.resultado.length; i++){
                    if(vm.resultado[i].user != null){
                        vm.pending.push(vm.resultado[i]);
                    }
                }
            });

            // solicitud pendiente --> eres el emisor
            Amistad.getSolicitudesPendientesEmisor(function(result){
                vm.resultado = result;
                for(var i = 0; i < vm.resultado.length; i++){
                    if(vm.resultado[i].user != null){
                        vm.pendingEmisor.push(vm.resultado[i]);
                    }
                }
            });

            // solicitud pendiente --> eres el receptor
            Amistad.getSolicitudesPendientesReceptor(function(result){
                vm.resultado = result;
                for(var i = 0; i < vm.resultado.length; i++){
                    if(vm.resultado[i].user != null){
                        vm.pendingReceptor.push(vm.resultado[i]);
                    }
                }
            });

            // resto de usuarios que no son amigos ni tienen solicitud
            UserExt.query(function(result) {
                vm.userExts = result;
                vm.otherUsers = result;
                vm.searchQuery = null;
                comprobarNoAmigos();
            });
        }

        vm.sendFriendRequest = function(id){
            Amistad.sendFriendRequest({'id': id}, {});
            $state.go('user-search', null, {reload:'user-search'});
        }

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
            vm.currentId = vm.currentAccount.id;
        });

        function comprobarNoAmigos() {
            // QUITAMOS LOS QUE TENEMOS AGREGADOS
            for(var i = 0; i < vm.otherUsers.length; i++){
                if(vm.accepted.length > 0){
                    for(var j = 0; j < vm.accepted.length; j++){
                        if(vm.otherUsers[i].user.id == vm.accepted[j].user.id){
                            vm.otherUsers.splice(i, 1);
                        }
                    }
                }
            }
            // QUITAMOS LOS DE SOL PENDIENTE
            for(var i = 0; i < vm.otherUsers.length; i++){
                if(vm.pending.length > 0){
                    for(var j = 0; j < vm.pending.length; j++){
                        if(vm.otherUsers[i].user.id == vm.pending[j].user.id){
                            vm.otherUsers.splice(i, 1);
                        }
                    }
                }

            }
        }
    }
})();
