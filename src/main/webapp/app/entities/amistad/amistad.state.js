(function () {
    'use strict';

    angular
        .module('doitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('amistad', {
                parent: 'entity',
                url: '/amistad',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'doitApp.amistad.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/amistad/amistads.html',
                        controller: 'AmistadController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('amistad');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('amistad-detail', {
                parent: 'amistad',
                url: '/amistad/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'doitApp.amistad.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/amistad/amistad-detail.html',
                        controller: 'AmistadDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('amistad');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Amistad', function ($stateParams, Amistad) {
                        return Amistad.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'amistad',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('amistad-detail.edit', {
                parent: 'amistad-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/amistad/amistad-dialog.html',
                        controller: 'AmistadDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Amistad', function (Amistad) {
                                return Amistad.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('amistad.new', {
                parent: 'amistad',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/amistad/amistad-dialog.html',
                        controller: 'AmistadDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    timeStamp: null,
                                    aceptada: null,
                                    horaRespuesta: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('amistad', null, {reload: 'amistad'});
                    }, function () {
                        $state.go('amistad');
                    });
                }]
            })
            .state('amistad.edit', {
                parent: 'amistad',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/amistad/amistad-dialog.html',
                        controller: 'AmistadDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Amistad', function (Amistad) {
                                return Amistad.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('amistad', null, {reload: 'amistad'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('amistad.delete', {
                parent: 'amistad',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/amistad/amistad-delete-dialog.html',
                        controller: 'AmistadDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Amistad', function (Amistad) {
                                return Amistad.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('amistad', null, {reload: 'amistad'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('amistades', {
                parent: 'amistad',
                url: '/solicitudes-amistad',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'doitApp.amistad.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/amistad/solicitudes-amistad.html',
                        controller: 'AmistadController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('amistad');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }

            })
    }

})();
