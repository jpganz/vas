(function() {
    'use strict';

    angular
        .module('ovasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('providercommand', {
            parent: 'entity',
            url: '/providercommand',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Providercommands'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/providercommand/providercommands.html',
                    controller: 'ProvidercommandController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('providercommand-detail', {
            parent: 'providercommand',
            url: '/providercommand/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Providercommand'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/providercommand/providercommand-detail.html',
                    controller: 'ProvidercommandDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Providercommand', function($stateParams, Providercommand) {
                    return Providercommand.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'providercommand',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('providercommand-detail.edit', {
            parent: 'providercommand-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/providercommand/providercommand-dialog.html',
                    controller: 'ProvidercommandDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Providercommand', function(Providercommand) {
                            return Providercommand.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('providercommand.new', {
            parent: 'providercommand',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/providercommand/providercommand-dialog.html',
                    controller: 'ProvidercommandDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                endpointUrl: null,
                                code: null,
                                retryLimit: null,
                                retryInterval: null,
                                communicationStandardId: null,
                                serviceSecurityId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('providercommand', null, { reload: 'providercommand' });
                }, function() {
                    $state.go('providercommand');
                });
            }]
        })
        .state('providercommand.edit', {
            parent: 'providercommand',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/providercommand/providercommand-dialog.html',
                    controller: 'ProvidercommandDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Providercommand', function(Providercommand) {
                            return Providercommand.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('providercommand', null, { reload: 'providercommand' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('providercommand.delete', {
            parent: 'providercommand',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/providercommand/providercommand-delete-dialog.html',
                    controller: 'ProvidercommandDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Providercommand', function(Providercommand) {
                            return Providercommand.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('providercommand', null, { reload: 'providercommand' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
