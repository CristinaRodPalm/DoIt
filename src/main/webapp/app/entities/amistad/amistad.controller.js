(function () {
    'use strict';

    angular
        .module('doitApp')
        .controller('AmistadController', AmistadController);

    AmistadController.$inject = ['Amistad'];

    function AmistadController(Amistad) {

        var vm = this;

        vm.amistads = [];
        vm.amistadsCurrentUser = [];

        loadAll();
        loadAmistadsCurrentUser();

        function loadAll() {
            Amistad.query(function (result) {
                vm.amistads = result;
                vm.searchQuery = null;
            });
        }

        function loadAmistadsCurrentUser() {
            Amistad.getAllByCurrentUser(function (result) {
                console.debug(result);
                vm.amistadsCurrentUser = result;
            });
        }
    }
})();
