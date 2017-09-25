(function() {
    'use strict';

    angular
        .module('ovasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('request-try', {
            parent: 'entity',
            url: '/request-try',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RequestTries'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/request-try/request-tries.html',
                    controller: 'RequestTryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('request-try-detail', {
            parent: 'request-try',
            url: '/request-try/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RequestTry'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/request-try/request-try-detail.html',
                    controller: 'RequestTryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RequestTry', function($stateParams, RequestTry) {
                    return RequestTry.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'request-try',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('request-try-detail.edit', {
            parent: 'request-try-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-try/request-try-dialog.html',
                    controller: 'RequestTryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RequestTry', function(RequestTry) {
                            return RequestTry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('request-try.new', {
            parent: 'request-try',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-try/request-try-dialog.html',
                    controller: 'RequestTryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('request-try', null, { reload: 'request-try' });
                }, function() {
                    $state.go('request-try');
                });
            }]
        })
        .state('request-try.edit', {
            parent: 'request-try',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-try/request-try-dialog.html',
                    controller: 'RequestTryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RequestTry', function(RequestTry) {
                            return RequestTry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('request-try', null, { reload: 'request-try' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('request-try.delete', {
            parent: 'request-try',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-try/request-try-delete-dialog.html',
                    controller: 'RequestTryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RequestTry', function(RequestTry) {
                            return RequestTry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('request-try', null, { reload: 'request-try' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
