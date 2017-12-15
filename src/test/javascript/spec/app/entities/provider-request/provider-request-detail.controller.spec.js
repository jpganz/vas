'use strict';

describe('Controller Tests', function() {

    describe('ProviderRequest Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProviderRequest, MockProviderCommand, MockRequest;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProviderRequest = jasmine.createSpy('MockProviderRequest');
            MockProviderCommand = jasmine.createSpy('MockProviderCommand');
            MockRequest = jasmine.createSpy('MockRequest');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ProviderRequest': MockProviderRequest,
                'ProviderCommand': MockProviderCommand,
                'Request': MockRequest
            };
            createController = function() {
                $injector.get('$controller')("ProviderRequestDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ovasApp:providerRequestUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
