(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('ParticipacionRetoDetailController', ParticipacionRetoDetailController);

    ParticipacionRetoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'ParticipacionReto', 'User', 'Reto', 'LikesReto'];

    function ParticipacionRetoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, ParticipacionReto, User, Reto, LikesReto) {
        var vm = this;

        vm.participacionReto = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('doitApp:participacionRetoUpdate', function(event, result) {
            vm.participacionReto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
