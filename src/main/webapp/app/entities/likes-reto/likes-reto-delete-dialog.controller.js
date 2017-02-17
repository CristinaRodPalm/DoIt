(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('LikesRetoDeleteController',LikesRetoDeleteController);

    LikesRetoDeleteController.$inject = ['$uibModalInstance', 'entity', 'LikesReto'];

    function LikesRetoDeleteController($uibModalInstance, entity, LikesReto) {
        var vm = this;

        vm.likesReto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LikesReto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
