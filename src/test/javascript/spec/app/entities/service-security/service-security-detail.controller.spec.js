'use strict';

describe('Controller Tests', function() {

    describe('ServiceSecurity Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockServiceSecurity, MockCommunicationStandard, MockSecurityParams, MockProviderCommand;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockServiceSecurity = jasmine.createSpy('MockServiceSecurity');
            MockCommunicationStandard = jasmine.createSpy('MockCommunicationStandard');
            MockSecurityParams = jasmine.createSpy('MockSecurityParams');
            MockProviderCommand = jasmine.createSpy('MockProviderCommand');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ServiceSecurity': MockServiceSecurity,
                'CommunicationStandard': MockCommunicationStandard,
                'SecurityParams': MockSecurityParams,
                'ProviderCommand': MockProviderCommand
            };
            createController = function() {
                $injector.get('$controller')("ServiceSecurityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ovasApp:serviceSecurityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
