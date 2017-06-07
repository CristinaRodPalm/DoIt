(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('ParticipacionRetoController', ParticipacionRetoController);

    ParticipacionRetoController.$inject = ['$state', 'DataUtils', 'ParticipacionReto', 'LikesReto'];

    function ParticipacionRetoController($state, DataUtils, ParticipacionReto, LikesReto) {

        var vm = this;

        vm.participacionRetos = [];
        vm.userParticipations = [];
        vm.challengeParticipations = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();
        loadUserParticipations();
        loadChallengeParticipations();

        function loadAll() {
            ParticipacionReto.query(function(result) {
                vm.participacionRetos = result;
                vm.searchQuery = null;
            });
        }
        function loadUserParticipations(){
            ParticipacionReto.userParticipations(function (result) {
                vm.userParticipations = result;

            })
        }
        function loadChallengeParticipations(){
            ParticipacionReto.challengeParticipations(function (result) {
                vm.challengeParticipations = result;
            })
        }

        vm.like = function(id){
            LikesReto.fav({'id':id},{});
            $state.reload();
        }
    }
})();
