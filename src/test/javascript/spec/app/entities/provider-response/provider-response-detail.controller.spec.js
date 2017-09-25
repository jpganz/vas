'use strict';

describe('Controller Tests', function() {

    describe('ProviderResponse Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProviderResponse, MockProviderCommand, MockResponseParameter;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProviderResponse = jasmine.createSpy('MockProviderResponse');
            MockProviderCommand = jasmine.createSpy('MockProviderCommand');
            MockResponseParameter = jasmine.createSpy('MockResponseParameter');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ProviderResponse': MockProviderResponse,
                'ProviderCommand': MockProviderCommand,
                'ResponseParameter': MockResponseParameter
            };
            createController = function() {
                $injector.get('$controller')("ProviderResponseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ovasApp:providerResponseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
