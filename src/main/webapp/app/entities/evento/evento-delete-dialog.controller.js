(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('EventoDeleteController',EventoDeleteController);

    EventoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Evento'];

    function EventoDeleteController($uibModalInstance, entity, Evento) {
        var vm = this;

        vm.evento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Evento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
