(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('RetoController', RetoController);

    RetoController.$inject = ['DataUtils', 'Reto', 'Principal', '$uibModal'];

    function RetoController(DataUtils, Reto, Principal, $uibModal) {

        var vm = this;

        vm.retos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.currentAccount;
        vm.openModal = openModal;

        loadAll();

        function loadAll() {
            Reto.query(function(result) {
                vm.retos = result;
                vm.searchQuery = null;
            });
        }

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });

        // abrir modal con la foto
        function openModal(data){
            var modalInstance = $uibModal.open({
                template: '<img style="heigth:100%;width:100%" data-ng-src="'+'data:'+data.imageContentType+';base64,'+data.imagen+'"</img>'
            });
        }
    }
})();
