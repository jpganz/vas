(function() {
    'use strict';

    angular
        .module('ovasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('request', {
            parent: 'entity',
            url: '/request',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Requests'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/request/requests.html',
                    controller: 'RequestController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('request-detail', {
            parent: 'request',
            url: '/request/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Request'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/request/request-detail.html',
                    controller: 'RequestDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Request', function($stateParams, Request) {
                    return Request.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'request',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('request-detail.edit', {
            parent: 'request-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request/request-dialog.html',
                    controller: 'RequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Request', function(Request) {
                            return Request.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('request.new', {
            parent: 'request',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request/request-dialog.html',
                    controller: 'RequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                requestAmount: null,
                                clientNumber: null,
                                annexedNumber: null,
                                clientName: null,
                                requestStatus: null,
                                statusRequested: null,
                                serviceCode: null,
                                eventConfirmation: null,
                                nextTryBy: null,
                                dateTime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('request', null, { reload: 'request' });
                }, function() {
                    $state.go('request');
                });
            }]
        })
        .state('request.edit', {
            parent: 'request',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request/request-dialog.html',
                    controller: 'RequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Request', function(Request) {
                            return Request.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('request', null, { reload: 'request' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('request.delete', {
            parent: 'request',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request/request-delete-dialog.html',
                    controller: 'RequestDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Request', function(Request) {
                            return Request.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('request', null, { reload: 'request' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
