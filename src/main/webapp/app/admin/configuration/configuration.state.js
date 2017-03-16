(function() {
    'use strict';

    angular
        .module('doitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('jhi-configuration', {
            parent: 'admin',
            url: '/configuration',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Configuración'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/configuration/configuration.html',
                    controller: 'JhiConfigurationController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
