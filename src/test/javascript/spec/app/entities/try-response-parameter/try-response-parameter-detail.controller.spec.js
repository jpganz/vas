'use strict';

describe('Controller Tests', function() {

    describe('TryResponseParameter Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTryResponseParameter, MockResponseParameter, MockRequestTry;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTryResponseParameter = jasmine.createSpy('MockTryResponseParameter');
            MockResponseParameter = jasmine.createSpy('MockResponseParameter');
            MockRequestTry = jasmine.createSpy('MockRequestTry');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TryResponseParameter': MockTryResponseParameter,
                'ResponseParameter': MockResponseParameter,
                'RequestTry': MockRequestTry
            };
            createController = function() {
                $injector.get('$controller')("TryResponseParameterDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ovasApp:tryResponseParameterUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
