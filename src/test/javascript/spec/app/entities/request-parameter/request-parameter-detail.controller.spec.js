'use strict';

describe('Controller Tests', function() {

    describe('RequestParameter Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRequestParameter, MockTryParameter, MockProviderCommand;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRequestParameter = jasmine.createSpy('MockRequestParameter');
            MockTryParameter = jasmine.createSpy('MockTryParameter');
            MockProviderCommand = jasmine.createSpy('MockProviderCommand');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'RequestParameter': MockRequestParameter,
                'TryParameter': MockTryParameter,
                'ProviderCommand': MockProviderCommand
            };
            createController = function() {
                $injector.get('$controller')("RequestParameterDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ovasApp:requestParameterUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
