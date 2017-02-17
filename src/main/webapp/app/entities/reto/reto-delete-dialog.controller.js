(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('RetoDeleteController',RetoDeleteController);

    RetoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Reto'];

    function RetoDeleteController($uibModalInstance, entity, Reto) {
        var vm = this;

        vm.reto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Reto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
