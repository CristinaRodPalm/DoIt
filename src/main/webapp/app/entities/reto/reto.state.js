(function () {
    'use strict';

    angular
        .module('doitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('reto', {
                parent: 'entity',
                url: '/reto',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'doitApp.reto.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/reto/retos.html',
                        controller: 'RetoController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('reto');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('reto-detail', {
                parent: 'reto',
                url: '/reto/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'doitApp.reto.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/reto/reto-detail.html',
                        controller: 'RetoDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('reto');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Reto', function ($stateParams, Reto) {
                        return Reto.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'reto',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('reto-detail.edit', {
                parent: 'reto-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/reto/reto-edit.html',
                        controller: 'RetoEditController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Reto', function (Reto) {
                                return Reto.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('reto.new', {
                parent: 'reto',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/reto/reto-dialog.html',
                        controller: 'RetoDialogController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            nombre: null,
                            descripcion: null,
                            horaPublicacion: null,
                            imagen: null,
                            imagenContentType: null,
                            id: null
                        };
                    }
                }
            })
            .state('reto.edit', {
                parent: 'reto',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/reto/reto-edit.html',
                        controller: 'RetoEditController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Reto', function (Reto) {
                                return Reto.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('lista-retos', null, {reload: 'lista-retos'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('reto.delete', {
                parent: 'reto',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/reto/reto-delete-dialog.html',
                        controller: 'RetoDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Reto', function (Reto) {
                                return Reto.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('lista-retos', null, {reload: 'reto-detail'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('lista-retos', {
                parent: 'reto',
                url: '/lista-retos',
                data: {
                    pageTitle: 'doitApp.reto.home.title',
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/reto/retos-list.html',
                        controller: 'RetoController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('reto');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            });
    }

})();
