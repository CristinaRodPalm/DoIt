(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('UserExtDetailController', UserExtDetailController);

    UserExtDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'UserExt', 'User'];

    function UserExtDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, UserExt, User) {
        var vm = this;

        vm.userExt = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('doitApp:userExtUpdate', function(event, result) {
            vm.userExt = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
