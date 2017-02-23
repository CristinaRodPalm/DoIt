(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('AmistadDeleteController',AmistadDeleteController);

    AmistadDeleteController.$inject = ['$uibModalInstance', 'entity', 'Amistad'];

    function AmistadDeleteController($uibModalInstance, entity, Amistad) {
        var vm = this;

        vm.amistad = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Amistad.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
