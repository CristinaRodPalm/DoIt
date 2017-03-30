(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('LikesRetoController', LikesRetoController);

    LikesRetoController.$inject = ['LikesReto'];

    function LikesRetoController(LikesReto) {

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
