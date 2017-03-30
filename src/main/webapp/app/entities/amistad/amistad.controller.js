(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('AmistadController', AmistadController);

    AmistadController.$inject = ['Amistad'];

    function AmistadController(Amistad) {

        var vm = this;

        vm.amistads = [];

        loadAll();

        function loadAll() {
            Amistad.query(function(result) {
                vm.amistads = result;
                vm.searchQuery = null;
            });
        }
    }
})();
