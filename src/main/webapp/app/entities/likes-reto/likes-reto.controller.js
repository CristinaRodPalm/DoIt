(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('LikesRetoController', LikesRetoController);

    LikesRetoController.$inject = ['$scope', '$state', 'LikesReto'];

    function LikesRetoController ($scope, $state, LikesReto) {
        var vm = this;

        vm.likesRetos = [];

        loadAll();

        function loadAll() {
            LikesReto.query(function(result) {
                vm.likesRetos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
