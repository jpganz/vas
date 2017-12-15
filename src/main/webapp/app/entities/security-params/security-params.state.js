(function() {
    'use strict';

    angular
        .module('ovasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('security-params', {
            parent: 'entity',
            url: '/security-params',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SecurityParams'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/security-params/security-params.html',
                    controller: 'SecurityParamsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('security-params-detail', {
            parent: 'security-params',
            url: '/security-params/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SecurityParams'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/security-params/security-params-detail.html',
                    controller: 'SecurityParamsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SecurityParams', function($stateParams, SecurityParams) {
                    return SecurityParams.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'security-params',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('security-params-detail.edit', {
            parent: 'security-params-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/security-params/security-params-dialog.html',
                    controller: 'SecurityParamsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SecurityParams', function(SecurityParams) {
                            return SecurityParams.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('security-params.new', {
            parent: 'security-params',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/security-params/security-params-dialog.html',
                    controller: 'SecurityParamsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                field: null,
                                value: null,
                                section: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('security-params', null, { reload: 'security-params' });
                }, function() {
                    $state.go('security-params');
                });
            }]
        })
        .state('security-params.edit', {
            parent: 'security-params',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/security-params/security-params-dialog.html',
                    controller: 'SecurityParamsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SecurityParams', function(SecurityParams) {
                            return SecurityParams.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('security-params', null, { reload: 'security-params' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('security-params.delete', {
            parent: 'security-params',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/security-params/security-params-delete-dialog.html',
                    controller: 'SecurityParamsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SecurityParams', function(SecurityParams) {
                            return SecurityParams.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('security-params', null, { reload: 'security-params' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
