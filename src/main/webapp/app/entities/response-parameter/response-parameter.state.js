(function() {
    'use strict';

    angular
        .module('ovasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('response-parameter', {
            parent: 'entity',
            url: '/response-parameter',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ResponseParameters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/response-parameter/response-parameters.html',
                    controller: 'ResponseParameterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('response-parameter-detail', {
            parent: 'response-parameter',
            url: '/response-parameter/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ResponseParameter'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/response-parameter/response-parameter-detail.html',
                    controller: 'ResponseParameterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ResponseParameter', function($stateParams, ResponseParameter) {
                    return ResponseParameter.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'response-parameter',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('response-parameter-detail.edit', {
            parent: 'response-parameter-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/response-parameter/response-parameter-dialog.html',
                    controller: 'ResponseParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ResponseParameter', function(ResponseParameter) {
                            return ResponseParameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('response-parameter.new', {
            parent: 'response-parameter',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/response-parameter/response-parameter-dialog.html',
                    controller: 'ResponseParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                type: null,
                                default_value: null,
                                section: null,
                                isMandatory: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('response-parameter', null, { reload: 'response-parameter' });
                }, function() {
                    $state.go('response-parameter');
                });
            }]
        })
        .state('response-parameter.edit', {
            parent: 'response-parameter',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/response-parameter/response-parameter-dialog.html',
                    controller: 'ResponseParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ResponseParameter', function(ResponseParameter) {
                            return ResponseParameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('response-parameter', null, { reload: 'response-parameter' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('response-parameter.delete', {
            parent: 'response-parameter',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/response-parameter/response-parameter-delete-dialog.html',
                    controller: 'ResponseParameterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ResponseParameter', function(ResponseParameter) {
                            return ResponseParameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('response-parameter', null, { reload: 'response-parameter' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
