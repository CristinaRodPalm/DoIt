(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('InvitacionEventoDeleteController',InvitacionEventoDeleteController);

    InvitacionEventoDeleteController.$inject = ['$uibModalInstance', 'entity', 'InvitacionEvento'];

    function InvitacionEventoDeleteController($uibModalInstance, entity, InvitacionEvento) {
        var vm = this;

        vm.invitacionEvento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            InvitacionEvento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
