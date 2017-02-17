(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('LikesRetoDetailController', LikesRetoDetailController);

    LikesRetoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LikesReto', 'User', 'ParticipacionReto'];

    function LikesRetoDetailController($scope, $rootScope, $stateParams, previousState, entity, LikesReto, User, ParticipacionReto) {
        var vm = this;

        vm.likesReto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('doitApp:likesRetoUpdate', function(event, result) {
            vm.likesReto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
