(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('UserExtDetailController', UserExtDetailController);

    UserExtDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'UserExt', 'User', 'Amistad', 'Principal'];

    function UserExtDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, UserExt, User, Amistad, Principal) {
        var vm = this;

        vm.currentAccount;
        vm.userExt = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.amistad;

        loadAmistad();

        vm.sendFriendRequest = function(id){
            Amistad.sendFriendRequest({'id': id}, {});
            //$state.go('user-search', null, {reload:'user-search'});
        }

        function loadAmistad() {
            vm.amistad=Amistad.getOneByUsers({'id': vm.userExt.user.id}, {});
            console.log("ID del perfil: "+vm.userExt.user.id);
            console.log(vm.amistad);
        }

        Principal.identity().then(function (account) {
            vm.currentAccount = account;
        });

        var unsubscribe = $rootScope.$on('doitApp:userExtUpdate', function(event, result) {
            vm.userExt = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
