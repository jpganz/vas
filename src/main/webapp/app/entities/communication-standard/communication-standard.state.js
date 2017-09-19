(function() {
    'use strict';

    angular
        .module('ovasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('communication-standard', {
            parent: 'entity',
            url: '/communication-standard',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CommunicationStandards'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/communication-standard/communication-standards.html',
                    controller: 'CommunicationStandardController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('communication-standard-detail', {
            parent: 'communication-standard',
            url: '/communication-standard/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CommunicationStandard'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/communication-standard/communication-standard-detail.html',
                    controller: 'CommunicationStandardDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CommunicationStandard', function($stateParams, CommunicationStandard) {
                    return CommunicationStandard.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'communication-standard',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('communication-standard-detail.edit', {
            parent: 'communication-standard-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/communication-standard/communication-standard-dialog.html',
                    controller: 'CommunicationStandardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CommunicationStandard', function(CommunicationStandard) {
                            return CommunicationStandard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('communication-standard.new', {
            parent: 'communication-standard',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/communication-standard/communication-standard-dialog.html',
                    controller: 'CommunicationStandardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('communication-standard', null, { reload: 'communication-standard' });
                }, function() {
                    $state.go('communication-standard');
                });
            }]
        })
        .state('communication-standard.edit', {
            parent: 'communication-standard',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/communication-standard/communication-standard-dialog.html',
                    controller: 'CommunicationStandardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CommunicationStandard', function(CommunicationStandard) {
                            return CommunicationStandard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('communication-standard', null, { reload: 'communication-standard' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('communication-standard.delete', {
            parent: 'communication-standard',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/communication-standard/communication-standard-delete-dialog.html',
                    controller: 'CommunicationStandardDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CommunicationStandard', function(CommunicationStandard) {
                            return CommunicationStandard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('communication-standard', null, { reload: 'communication-standard' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
