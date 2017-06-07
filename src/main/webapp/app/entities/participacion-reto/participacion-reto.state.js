(function () {
    'use strict';

    angular
        .module('doitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('participacion-reto', {
                parent: 'entity',
                url: '/participacion-reto',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'doitApp.participacionReto.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/participacion-reto/participacion-retos.html',
                        controller: 'ParticipacionRetoController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('participacionReto');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('participacion-reto-detail', {
                parent: 'participacion-reto',
                url: '/participacion-reto/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'doitApp.participacionReto.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/participacion-reto/participacion-reto-detail.html',
                        controller: 'ParticipacionRetoDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('participacionReto');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ParticipacionReto', function ($stateParams, ParticipacionReto) {
                        return ParticipacionReto.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'participacion-reto',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('participacion-reto-detail.edit', {
                parent: 'participacion-reto-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/participacion-reto/participacion-reto-dialog.html',
                        controller: 'ParticipacionRetoDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['ParticipacionReto', function (ParticipacionReto) {
                                return ParticipacionReto.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('participacion-reto.new', {
                parent: 'participacion-reto',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/participacion-reto/participacion-reto-dialog.html',
                        controller: 'ParticipacionRetoDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    imagen: null,
                                    imagenContentType: null,
                                    horaPublicacion: null,
                                    descripcion: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('participacion-reto', null, {reload: 'participacion-reto'});
                    }, function () {
                        $state.go('participacion-reto');
                    });
                }]
            })
            .state('participacion-reto.edit', {
                parent: 'participacion-reto',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/participacion-reto/participacion-reto-dialog.html',
                        controller: 'ParticipacionRetoDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['ParticipacionReto', function (ParticipacionReto) {
                                return ParticipacionReto.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('participacion-reto', null, {reload: 'participacion-reto'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('participacion-reto.delete', {
                parent: 'participacion-reto',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/participacion-reto/participacion-reto-delete-dialog.html',
                        controller: 'ParticipacionRetoDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['ParticipacionReto', function (ParticipacionReto) {
                                return ParticipacionReto.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('participacion-reto', null, {reload: 'participacion-reto'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('user-participations', {
                parent: 'participacion-reto',
                url: '/user-participations',
                data: {
                    pageTitle: 'doitApp.participacionReto.user-part-title',
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/participacion-reto/participacion-user.html',
                        controller: 'ParticipacionRetoController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('participacionReto');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('reto-participations', {
                parent: 'participacion-reto',
                url: '/{id}/reto-participations',
                data: {
                    pageTitle: 'doitApp.participacionReto.challenge-part-title',
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/participacion-reto/participaciones.html',
                        controller: 'ParticipacionRetoController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('participacionReto');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            });
    }
})();
