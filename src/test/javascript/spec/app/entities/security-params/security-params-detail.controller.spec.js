'use strict';

describe('Controller Tests', function() {

    describe('SecurityParams Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSecurityParams, MockServiceSecurity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSecurityParams = jasmine.createSpy('MockSecurityParams');
            MockServiceSecurity = jasmine.createSpy('MockServiceSecurity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SecurityParams': MockSecurityParams,
                'ServiceSecurity': MockServiceSecurity
            };
            createController = function() {
                $injector.get('$controller')("SecurityParamsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ovasApp:securityParamsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
