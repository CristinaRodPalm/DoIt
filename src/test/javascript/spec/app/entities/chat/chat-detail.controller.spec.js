'use strict';

describe('Controller Tests', function() {

    describe('Chat Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockChat, MockEvento, MockMensaje;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockChat = jasmine.createSpy('MockChat');
            MockEvento = jasmine.createSpy('MockEvento');
            MockMensaje = jasmine.createSpy('MockMensaje');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Chat': MockChat,
                'Evento': MockEvento,
                'Mensaje': MockMensaje
            };
            createController = function() {
                $injector.get('$controller')("ChatDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'doitApp:chatUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
