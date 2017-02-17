'use strict';

describe('Controller Tests', function() {

    describe('Evento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEvento, MockReto, MockUser, MockInvitacionEvento, MockChat;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEvento = jasmine.createSpy('MockEvento');
            MockReto = jasmine.createSpy('MockReto');
            MockUser = jasmine.createSpy('MockUser');
            MockInvitacionEvento = jasmine.createSpy('MockInvitacionEvento');
            MockChat = jasmine.createSpy('MockChat');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Evento': MockEvento,
                'Reto': MockReto,
                'User': MockUser,
                'InvitacionEvento': MockInvitacionEvento,
                'Chat': MockChat
            };
            createController = function() {
                $injector.get('$controller')("EventoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'doitApp:eventoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
