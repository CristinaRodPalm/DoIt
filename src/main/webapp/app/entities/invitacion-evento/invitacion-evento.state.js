(function() {
    'use strict';

    angular
        .module('doitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('invitacion-evento', {
            parent: 'entity',
            url: '/invitacion-evento',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'doitApp.invitacionEvento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/invitacion-evento/invitacion-eventos.html',
                    controller: 'InvitacionEventoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('invitacionEvento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('invitacion-evento-detail', {
            parent: 'invitacion-evento',
            url: '/invitacion-evento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'doitApp.invitacionEvento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/invitacion-evento/invitacion-evento-detail.html',
                    controller: 'InvitacionEventoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('invitacionEvento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'InvitacionEvento', function($stateParams, InvitacionEvento) {
                    return InvitacionEvento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'invitacion-evento',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('invitacion-evento-detail.edit', {
            parent: 'invitacion-evento-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/invitacion-evento/invitacion-evento-dialog.html',
                    controller: 'InvitacionEventoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InvitacionEvento', function(InvitacionEvento) {
                            return InvitacionEvento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('invitacion-evento.new', {
            parent: 'invitacion-evento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/invitacion-evento/invitacion-evento-dialog.html',
                    controller: 'InvitacionEventoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                horaResolucion: null,
                                horaInvitacion: null,
                                resolucion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('invitacion-evento', null, { reload: 'invitacion-evento' });
                }, function() {
                    $state.go('invitacion-evento');
                });
            }]
        })
        .state('invitacion-evento.edit', {
            parent: 'invitacion-evento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/invitacion-evento/invitacion-evento-dialog.html',
                    controller: 'InvitacionEventoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InvitacionEvento', function(InvitacionEvento) {
                            return InvitacionEvento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('invitacion-evento', null, { reload: 'invitacion-evento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('invitacion-evento.delete', {
            parent: 'invitacion-evento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/invitacion-evento/invitacion-evento-delete-dialog.html',
                    controller: 'InvitacionEventoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['InvitacionEvento', function(InvitacionEvento) {
                            return InvitacionEvento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('invitacion-evento', null, { reload: 'invitacion-evento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state("invitaciones-pendientes",{
            parent:'entity',
            url:'/pending-invitations',
            data:{
                authorities:['ROLE_USER'],
                pageTitle:'doitApp.invitacionEvento.detail.title'
            },
            views:{
                'content@':{
                    templateUrl:'app/entities/invitacion-evento/invitaciones-evento-pendientes.html',
                    controller:'InvitacionEventoController',
                    controllerAs:'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('invitacionEvento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });
    }

})();
