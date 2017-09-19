(function() {
    'use strict';

    angular
        .module('ovasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('request-parameter', {
            parent: 'entity',
            url: '/request-parameter',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RequestParameters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/request-parameter/request-parameters.html',
                    controller: 'RequestParameterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('request-parameter-detail', {
            parent: 'request-parameter',
            url: '/request-parameter/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RequestParameter'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/request-parameter/request-parameter-detail.html',
                    controller: 'RequestParameterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RequestParameter', function($stateParams, RequestParameter) {
                    return RequestParameter.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'request-parameter',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('request-parameter-detail.edit', {
            parent: 'request-parameter-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-parameter/request-parameter-dialog.html',
                    controller: 'RequestParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RequestParameter', function(RequestParameter) {
                            return RequestParameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('request-parameter.new', {
            parent: 'request-parameter',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-parameter/request-parameter-dialog.html',
                    controller: 'RequestParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                type: null,
                                defaultValue: null,
                                section: null,
                                providerCommandId: null,
                                isMandatory: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('request-parameter', null, { reload: 'request-parameter' });
                }, function() {
                    $state.go('request-parameter');
                });
            }]
        })
        .state('request-parameter.edit', {
            parent: 'request-parameter',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-parameter/request-parameter-dialog.html',
                    controller: 'RequestParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RequestParameter', function(RequestParameter) {
                            return RequestParameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('request-parameter', null, { reload: 'request-parameter' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('request-parameter.delete', {
            parent: 'request-parameter',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-parameter/request-parameter-delete-dialog.html',
                    controller: 'RequestParameterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RequestParameter', function(RequestParameter) {
                            return RequestParameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('request-parameter', null, { reload: 'request-parameter' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
