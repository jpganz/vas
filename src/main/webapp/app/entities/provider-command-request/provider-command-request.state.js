(function() {
    'use strict';

    angular
        .module('ovasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('provider-command-request', {
            parent: 'entity',
            url: '/provider-command-request',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProviderCommandRequests'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/provider-command-request/provider-command-requests.html',
                    controller: 'ProviderCommandRequestController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('provider-command-request-detail', {
            parent: 'provider-command-request',
            url: '/provider-command-request/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProviderCommandRequest'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/provider-command-request/provider-command-request-detail.html',
                    controller: 'ProviderCommandRequestDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ProviderCommandRequest', function($stateParams, ProviderCommandRequest) {
                    return ProviderCommandRequest.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'provider-command-request',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('provider-command-request-detail.edit', {
            parent: 'provider-command-request-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-command-request/provider-command-request-dialog.html',
                    controller: 'ProviderCommandRequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProviderCommandRequest', function(ProviderCommandRequest) {
                            return ProviderCommandRequest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('provider-command-request.new', {
            parent: 'provider-command-request',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-command-request/provider-command-request-dialog.html',
                    controller: 'ProviderCommandRequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                requestId: null,
                                providerCommandId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('provider-command-request', null, { reload: 'provider-command-request' });
                }, function() {
                    $state.go('provider-command-request');
                });
            }]
        })
        .state('provider-command-request.edit', {
            parent: 'provider-command-request',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-command-request/provider-command-request-dialog.html',
                    controller: 'ProviderCommandRequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProviderCommandRequest', function(ProviderCommandRequest) {
                            return ProviderCommandRequest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('provider-command-request', null, { reload: 'provider-command-request' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('provider-command-request.delete', {
            parent: 'provider-command-request',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-command-request/provider-command-request-delete-dialog.html',
                    controller: 'ProviderCommandRequestDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProviderCommandRequest', function(ProviderCommandRequest) {
                            return ProviderCommandRequest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('provider-command-request', null, { reload: 'provider-command-request' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
