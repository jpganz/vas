(function() {
    'use strict';

    angular
        .module('ovasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('service-security', {
            parent: 'entity',
            url: '/service-security',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ServiceSecurities'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/service-security/service-securities.html',
                    controller: 'ServiceSecurityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('service-security-detail', {
            parent: 'service-security',
            url: '/service-security/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ServiceSecurity'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/service-security/service-security-detail.html',
                    controller: 'ServiceSecurityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ServiceSecurity', function($stateParams, ServiceSecurity) {
                    return ServiceSecurity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'service-security',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('service-security-detail.edit', {
            parent: 'service-security-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-security/service-security-dialog.html',
                    controller: 'ServiceSecurityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ServiceSecurity', function(ServiceSecurity) {
                            return ServiceSecurity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('service-security.new', {
            parent: 'service-security',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-security/service-security-dialog.html',
                    controller: 'ServiceSecurityDialogController',
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
                    $state.go('service-security', null, { reload: 'service-security' });
                }, function() {
                    $state.go('service-security');
                });
            }]
        })
        .state('service-security.edit', {
            parent: 'service-security',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-security/service-security-dialog.html',
                    controller: 'ServiceSecurityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ServiceSecurity', function(ServiceSecurity) {
                            return ServiceSecurity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('service-security', null, { reload: 'service-security' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('service-security.delete', {
            parent: 'service-security',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-security/service-security-delete-dialog.html',
                    controller: 'ServiceSecurityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ServiceSecurity', function(ServiceSecurity) {
                            return ServiceSecurity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('service-security', null, { reload: 'service-security' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
