(function() {
    'use strict';

    angular
        .module('ovasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('provider-request', {
            parent: 'entity',
            url: '/provider-request',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProviderRequests'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/provider-request/provider-requests.html',
                    controller: 'ProviderRequestController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('provider-request-detail', {
            parent: 'provider-request',
            url: '/provider-request/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProviderRequest'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/provider-request/provider-request-detail.html',
                    controller: 'ProviderRequestDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ProviderRequest', function($stateParams, ProviderRequest) {
                    return ProviderRequest.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'provider-request',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('provider-request-detail.edit', {
            parent: 'provider-request-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-request/provider-request-dialog.html',
                    controller: 'ProviderRequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProviderRequest', function(ProviderRequest) {
                            return ProviderRequest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('provider-request.new', {
            parent: 'provider-request',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-request/provider-request-dialog.html',
                    controller: 'ProviderRequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('provider-request', null, { reload: 'provider-request' });
                }, function() {
                    $state.go('provider-request');
                });
            }]
        })
        .state('provider-request.edit', {
            parent: 'provider-request',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-request/provider-request-dialog.html',
                    controller: 'ProviderRequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProviderRequest', function(ProviderRequest) {
                            return ProviderRequest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('provider-request', null, { reload: 'provider-request' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('provider-request.delete', {
            parent: 'provider-request',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-request/provider-request-delete-dialog.html',
                    controller: 'ProviderRequestDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProviderRequest', function(ProviderRequest) {
                            return ProviderRequest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('provider-request', null, { reload: 'provider-request' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
