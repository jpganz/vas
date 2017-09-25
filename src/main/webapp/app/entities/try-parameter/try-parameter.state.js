(function() {
    'use strict';

    angular
        .module('ovasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('try-parameter', {
            parent: 'entity',
            url: '/try-parameter',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TryParameters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/try-parameter/try-parameters.html',
                    controller: 'TryParameterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('try-parameter-detail', {
            parent: 'try-parameter',
            url: '/try-parameter/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TryParameter'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/try-parameter/try-parameter-detail.html',
                    controller: 'TryParameterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TryParameter', function($stateParams, TryParameter) {
                    return TryParameter.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'try-parameter',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('try-parameter-detail.edit', {
            parent: 'try-parameter-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/try-parameter/try-parameter-dialog.html',
                    controller: 'TryParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TryParameter', function(TryParameter) {
                            return TryParameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('try-parameter.new', {
            parent: 'try-parameter',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/try-parameter/try-parameter-dialog.html',
                    controller: 'TryParameterDialogController',
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
                    $state.go('try-parameter', null, { reload: 'try-parameter' });
                }, function() {
                    $state.go('try-parameter');
                });
            }]
        })
        .state('try-parameter.edit', {
            parent: 'try-parameter',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/try-parameter/try-parameter-dialog.html',
                    controller: 'TryParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TryParameter', function(TryParameter) {
                            return TryParameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('try-parameter', null, { reload: 'try-parameter' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('try-parameter.delete', {
            parent: 'try-parameter',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/try-parameter/try-parameter-delete-dialog.html',
                    controller: 'TryParameterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TryParameter', function(TryParameter) {
                            return TryParameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('try-parameter', null, { reload: 'try-parameter' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
