(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('AmistadDetailController', AmistadDetailController);

    AmistadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Amistad', 'User'];

    function AmistadDetailController($scope, $rootScope, $stateParams, previousState, entity, Amistad, User) {
        var vm = this;

        vm.amistad = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('doitApp:amistadUpdate', function(event, result) {
            vm.amistad = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
