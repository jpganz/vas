'use strict';

describe('Controller Tests', function() {

    describe('Providercommand Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProvidercommand, MockProvider, MockCommunicationStandard, MockCommand;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProvidercommand = jasmine.createSpy('MockProvidercommand');
            MockProvider = jasmine.createSpy('MockProvider');
            MockCommunicationStandard = jasmine.createSpy('MockCommunicationStandard');
            MockCommand = jasmine.createSpy('MockCommand');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Providercommand': MockProvidercommand,
                'Provider': MockProvider,
                'CommunicationStandard': MockCommunicationStandard,
                'Command': MockCommand
            };
            createController = function() {
                $injector.get('$controller')("ProvidercommandDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ovasApp:providercommandUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
