(function() {
    'use strict';

    angular
        .module('ovasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sys-options', {
            parent: 'entity',
            url: '/sys-options',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SysOptions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sys-options/sys-options.html',
                    controller: 'SysOptionsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('sys-options-detail', {
            parent: 'sys-options',
            url: '/sys-options/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SysOptions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sys-options/sys-options-detail.html',
                    controller: 'SysOptionsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SysOptions', function($stateParams, SysOptions) {
                    return SysOptions.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sys-options',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sys-options-detail.edit', {
            parent: 'sys-options-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sys-options/sys-options-dialog.html',
                    controller: 'SysOptionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SysOptions', function(SysOptions) {
                            return SysOptions.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sys-options.new', {
            parent: 'sys-options',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sys-options/sys-options-dialog.html',
                    controller: 'SysOptionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                option_name: null,
                                option_value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sys-options', null, { reload: 'sys-options' });
                }, function() {
                    $state.go('sys-options');
                });
            }]
        })
        .state('sys-options.edit', {
            parent: 'sys-options',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sys-options/sys-options-dialog.html',
                    controller: 'SysOptionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SysOptions', function(SysOptions) {
                            return SysOptions.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sys-options', null, { reload: 'sys-options' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sys-options.delete', {
            parent: 'sys-options',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sys-options/sys-options-delete-dialog.html',
                    controller: 'SysOptionsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SysOptions', function(SysOptions) {
                            return SysOptions.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sys-options', null, { reload: 'sys-options' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
