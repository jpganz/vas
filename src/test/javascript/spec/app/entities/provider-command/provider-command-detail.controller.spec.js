'use strict';

describe('Controller Tests', function() {

    describe('ProviderCommand Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProviderCommand, MockProvider, MockCommunicationStandard, MockProviderResponse, MockRequestParameter, MockCommand, MockServiceSecurity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProviderCommand = jasmine.createSpy('MockProviderCommand');
            MockProvider = jasmine.createSpy('MockProvider');
            MockCommunicationStandard = jasmine.createSpy('MockCommunicationStandard');
            MockProviderResponse = jasmine.createSpy('MockProviderResponse');
            MockRequestParameter = jasmine.createSpy('MockRequestParameter');
            MockCommand = jasmine.createSpy('MockCommand');
            MockServiceSecurity = jasmine.createSpy('MockServiceSecurity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ProviderCommand': MockProviderCommand,
                'Provider': MockProvider,
                'CommunicationStandard': MockCommunicationStandard,
                'ProviderResponse': MockProviderResponse,
                'RequestParameter': MockRequestParameter,
                'Command': MockCommand,
                'ServiceSecurity': MockServiceSecurity
            };
            createController = function() {
                $injector.get('$controller')("ProviderCommandDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ovasApp:providerCommandUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
