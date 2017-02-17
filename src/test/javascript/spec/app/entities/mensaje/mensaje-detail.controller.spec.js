'use strict';

describe('Controller Tests', function() {

    describe('Mensaje Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMensaje, MockUser, MockChat;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMensaje = jasmine.createSpy('MockMensaje');
            MockUser = jasmine.createSpy('MockUser');
            MockChat = jasmine.createSpy('MockChat');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Mensaje': MockMensaje,
                'User': MockUser,
                'Chat': MockChat
            };
            createController = function() {
                $injector.get('$controller')("MensajeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'doitApp:mensajeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
