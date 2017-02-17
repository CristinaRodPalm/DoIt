(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('ParticipacionRetoDeleteController',ParticipacionRetoDeleteController);

    ParticipacionRetoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ParticipacionReto'];

    function ParticipacionRetoDeleteController($uibModalInstance, entity, ParticipacionReto) {
        var vm = this;

        vm.participacionReto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ParticipacionReto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
