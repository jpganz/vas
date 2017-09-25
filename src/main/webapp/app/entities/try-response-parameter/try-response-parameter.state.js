(function() {
    'use strict';

    angular
        .module('ovasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('try-response-parameter', {
            parent: 'entity',
            url: '/try-response-parameter',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TryResponseParameters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/try-response-parameter/try-response-parameters.html',
                    controller: 'TryResponseParameterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('try-response-parameter-detail', {
            parent: 'try-response-parameter',
            url: '/try-response-parameter/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TryResponseParameter'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/try-response-parameter/try-response-parameter-detail.html',
                    controller: 'TryResponseParameterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TryResponseParameter', function($stateParams, TryResponseParameter) {
                    return TryResponseParameter.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'try-response-parameter',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('try-response-parameter-detail.edit', {
            parent: 'try-response-parameter-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/try-response-parameter/try-response-parameter-dialog.html',
                    controller: 'TryResponseParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TryResponseParameter', function(TryResponseParameter) {
                            return TryResponseParameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('try-response-parameter.new', {
            parent: 'try-response-parameter',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/try-response-parameter/try-response-parameter-dialog.html',
                    controller: 'TryResponseParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('try-response-parameter', null, { reload: 'try-response-parameter' });
                }, function() {
                    $state.go('try-response-parameter');
                });
            }]
        })
        .state('try-response-parameter.edit', {
            parent: 'try-response-parameter',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/try-response-parameter/try-response-parameter-dialog.html',
                    controller: 'TryResponseParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TryResponseParameter', function(TryResponseParameter) {
                            return TryResponseParameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('try-response-parameter', null, { reload: 'try-response-parameter' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('try-response-parameter.delete', {
            parent: 'try-response-parameter',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/try-response-parameter/try-response-parameter-delete-dialog.html',
                    controller: 'TryResponseParameterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TryResponseParameter', function(TryResponseParameter) {
                            return TryResponseParameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('try-response-parameter', null, { reload: 'try-response-parameter' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
