(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('AmistadController', AmistadController);

    AmistadController.$inject = ['$scope', '$state', 'Amistad'];

    function AmistadController ($scope, $state, Amistad) {
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
