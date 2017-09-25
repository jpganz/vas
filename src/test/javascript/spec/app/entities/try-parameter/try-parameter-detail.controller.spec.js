'use strict';

describe('Controller Tests', function() {

    describe('TryParameter Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTryParameter, MockRequest, MockRequestParameter;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTryParameter = jasmine.createSpy('MockTryParameter');
            MockRequest = jasmine.createSpy('MockRequest');
            MockRequestParameter = jasmine.createSpy('MockRequestParameter');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TryParameter': MockTryParameter,
                'Request': MockRequest,
                'RequestParameter': MockRequestParameter
            };
            createController = function() {
                $injector.get('$controller')("TryParameterDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ovasApp:tryParameterUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
