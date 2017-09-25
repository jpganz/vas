'use strict';

describe('Controller Tests', function() {

    describe('ResponseParameter Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockResponseParameter, MockProviderResponse, MockTryResponseParameter;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockResponseParameter = jasmine.createSpy('MockResponseParameter');
            MockProviderResponse = jasmine.createSpy('MockProviderResponse');
            MockTryResponseParameter = jasmine.createSpy('MockTryResponseParameter');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ResponseParameter': MockResponseParameter,
                'ProviderResponse': MockProviderResponse,
                'TryResponseParameter': MockTryResponseParameter
            };
            createController = function() {
                $injector.get('$controller')("ResponseParameterDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ovasApp:responseParameterUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
