(function() {
    'use strict';

    angular
        .module('ovasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('provider-response', {
            parent: 'entity',
            url: '/provider-response',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProviderResponses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/provider-response/provider-responses.html',
                    controller: 'ProviderResponseController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('provider-response-detail', {
            parent: 'provider-response',
            url: '/provider-response/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ProviderResponse'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/provider-response/provider-response-detail.html',
                    controller: 'ProviderResponseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ProviderResponse', function($stateParams, ProviderResponse) {
                    return ProviderResponse.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'provider-response',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('provider-response-detail.edit', {
            parent: 'provider-response-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-response/provider-response-dialog.html',
                    controller: 'ProviderResponseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProviderResponse', function(ProviderResponse) {
                            return ProviderResponse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('provider-response.new', {
            parent: 'provider-response',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-response/provider-response-dialog.html',
                    controller: 'ProviderResponseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                emailNotify: null,
                                emailAddressToNotify: null,
                                addToRetry: null,
                                type: null,
                                providerCommandId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('provider-response', null, { reload: 'provider-response' });
                }, function() {
                    $state.go('provider-response');
                });
            }]
        })
        .state('provider-response.edit', {
            parent: 'provider-response',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-response/provider-response-dialog.html',
                    controller: 'ProviderResponseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProviderResponse', function(ProviderResponse) {
                            return ProviderResponse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('provider-response', null, { reload: 'provider-response' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('provider-response.delete', {
            parent: 'provider-response',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/provider-response/provider-response-delete-dialog.html',
                    controller: 'ProviderResponseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProviderResponse', function(ProviderResponse) {
                            return ProviderResponse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('provider-response', null, { reload: 'provider-response' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
