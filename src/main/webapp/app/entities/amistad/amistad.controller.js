(function () {
    'use strict';

    angular
        .module('doitApp')
        .controller('AmistadController', AmistadController);

    AmistadController.$inject = ['Amistad', '$state'];

    function AmistadController(Amistad, $state) {

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
                vm.amistadsCurrentUser = result;
            });
        }

        vm.acceptFriend = function(id){
            Amistad.accept({'id':id}, {});
            alert("AMISTAD ACEPTADA");
            $state.go('amistades', null, {reload: 'amistades'});
        }

        vm.denyFriend = function(id){
            Amistad.deny({'id':id}, {});
            alert("AMISTAD RECHAZADA");
            $state.go('amistades', null, {reload: 'amistades'});
        }
    }
})();
