(function() {
    'use strict';

    angular
        .module('ovasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('provider-command', {
            parent: 'entity',
            url: '/provider-command',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProviderCommands'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/provider-command/provider-commands.html',
                    controller: 'ProviderCommandController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('provider-command-detail', {
            parent: 'provider-command',
            url: '/provider-command/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProviderCommand'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/provider-command/provider-command-detail.html',
                    controller: 'ProviderCommandDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ProviderCommand', function($stateParams, ProviderCommand) {
                    return ProviderCommand.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'provider-command',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('provider-command-detail.edit', {
            parent: 'provider-command-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-command/provider-command-dialog.html',
                    controller: 'ProviderCommandDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProviderCommand', function(ProviderCommand) {
                            return ProviderCommand.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('provider-command.new', {
            parent: 'provider-command',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-command/provider-command-dialog.html',
                    controller: 'ProviderCommandDialogController',
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
                                emailNotify: null,
                                emailAddressToNotify: null,
                                addToRetry: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('provider-command', null, { reload: 'provider-command' });
                }, function() {
                    $state.go('provider-command');
                });
            }]
        })
        .state('provider-command.edit', {
            parent: 'provider-command',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-command/provider-command-dialog.html',
                    controller: 'ProviderCommandDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProviderCommand', function(ProviderCommand) {
                            return ProviderCommand.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('provider-command', null, { reload: 'provider-command' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('provider-command.delete', {
            parent: 'provider-command',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-command/provider-command-delete-dialog.html',
                    controller: 'ProviderCommandDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProviderCommand', function(ProviderCommand) {
                            return ProviderCommand.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('provider-command', null, { reload: 'provider-command' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
