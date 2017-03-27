(function () {
    'use strict';

    angular
        .module('doitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('evento', {
                parent: 'entity',
                url: '/evento',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Eventos'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/evento/eventos.html',
                        controller: 'EventoController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
            .state('evento-detail', {
                parent: 'evento',
                url: '/evento/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Evento'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/evento/evento-detail.html',
                        controller: 'EventoDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Evento', function ($stateParams, Evento) {
                        return Evento.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'eventos',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('evento-detail.edit', {
                parent: 'evento-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/evento/evento-dialog.html',
                        controller: 'EventoDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Evento', function (Evento) {
                                return Evento.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('evento.new', {
                parent: 'evento',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/evento/evento-dialog.html',
                        controller: 'EventoDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nombre: null,
                                    descripcion: null,
                                    hora: null,
                                    imagen: null,
                                    imagenContentType: null,
                                    urlMaps: null,
                                    latitud: null,
                                    longitud: null,
                                    fechaEvento: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('evento', null, {reload: 'evento'});
                    }, function () {
                        $state.go('evento');
                    });
                }]
            })
            .state('evento.edit', {
                parent: 'evento',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/evento/evento-dialog.html',
                        controller: 'EventoDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Evento', function (Evento) {
                                return Evento.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('evento', null, {reload: 'evento'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('evento.delete', {
                parent: 'evento',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/evento/evento-delete-dialog.html',
                        controller: 'EventoDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Evento', function (Evento) {
                                return Evento.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('evento', null, {reload: 'evento'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('eventos', {
                parent: 'entity',
                url: '/lista-eventos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Eventos'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/evento/lista-eventos.html',
                        controller: 'EventoController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
            .state('crear-evento', {
                parent: 'evento',
                url: '/crear-evento',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Crear evento'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/evento/crear-evento.html',
                        controller: 'EventoDialogController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
            .state('evento-search', {
                parent: 'evento',
                url: '/buscar-eventos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Buscar eventos'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/evento/evento-search.html',
                        controller: 'EventoController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
            .state('evento-map', {
                parent: 'evento',
                url: '/eventos-mundo',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Lugar de evento'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/evento/eventos-mundo.html',
                        controller: 'mapController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
    }

})();
