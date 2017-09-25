'use strict';

describe('Controller Tests', function() {

    describe('RequestTry Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRequestTry, MockRequest, MockTryResponseParameter;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRequestTry = jasmine.createSpy('MockRequestTry');
            MockRequest = jasmine.createSpy('MockRequest');
            MockTryResponseParameter = jasmine.createSpy('MockTryResponseParameter');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'RequestTry': MockRequestTry,
                'Request': MockRequest,
                'TryResponseParameter': MockTryResponseParameter
            };
            createController = function() {
                $injector.get('$controller')("RequestTryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ovasApp:requestTryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
