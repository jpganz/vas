'use strict';

describe('Controller Tests', function() {

    describe('CommunicationStandard Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCommunicationStandard;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCommunicationStandard = jasmine.createSpy('MockCommunicationStandard');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CommunicationStandard': MockCommunicationStandard
            };
            createController = function() {
                $injector.get('$controller')("CommunicationStandardDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ovasApp:communicationStandardUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
