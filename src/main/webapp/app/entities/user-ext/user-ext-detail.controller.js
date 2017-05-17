(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('UserExtDetailController', UserExtDetailController);

    UserExtDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'UserExt', 'User', 'Amistad'];

    function UserExtDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, UserExt, User, Amistad) {
        var vm = this;

        vm.userExt = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        vm.sendFriendRequest = function(id){
            Amistad.sendFriendRequest({'id': id}, {});
            //$state.go('user-search', null, {reload:'user-search'});
        }

        var unsubscribe = $rootScope.$on('doitApp:userExtUpdate', function(event, result) {
            vm.userExt = result;
        });
        console.log(vm.userExt);
        $scope.$on('$destroy', unsubscribe);
    }
})();
