(function() {
    'use strict';

    angular
        .module('doitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('likes-reto', {
            parent: 'entity',
            url: '/likes-reto',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'doitApp.likesReto.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/likes-reto/likes-retos.html',
                    controller: 'LikesRetoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('likesReto');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('likes-reto-detail', {
            parent: 'likes-reto',
            url: '/likes-reto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'doitApp.likesReto.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/likes-reto/likes-reto-detail.html',
                    controller: 'LikesRetoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('likesReto');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LikesReto', function($stateParams, LikesReto) {
                    return LikesReto.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'likes-reto',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('likes-reto-detail.edit', {
            parent: 'likes-reto-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/likes-reto/likes-reto-dialog.html',
                    controller: 'LikesRetoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LikesReto', function(LikesReto) {
                            return LikesReto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('likes-reto.new', {
            parent: 'likes-reto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/likes-reto/likes-reto-dialog.html',
                    controller: 'LikesRetoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                puntuacion: null,
                                horaLike: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('likes-reto', null, { reload: 'likes-reto' });
                }, function() {
                    $state.go('likes-reto');
                });
            }]
        })
        .state('likes-reto.edit', {
            parent: 'likes-reto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/likes-reto/likes-reto-dialog.html',
                    controller: 'LikesRetoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LikesReto', function(LikesReto) {
                            return LikesReto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('likes-reto', null, { reload: 'likes-reto' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('likes-reto.delete', {
            parent: 'likes-reto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/likes-reto/likes-reto-delete-dialog.html',
                    controller: 'LikesRetoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LikesReto', function(LikesReto) {
                            return LikesReto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('likes-reto', null, { reload: 'likes-reto' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
