'use strict';

describe('Controller Tests', function() {

    describe('ParticipacionReto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockParticipacionReto, MockUser, MockReto, MockLikesReto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockParticipacionReto = jasmine.createSpy('MockParticipacionReto');
            MockUser = jasmine.createSpy('MockUser');
            MockReto = jasmine.createSpy('MockReto');
            MockLikesReto = jasmine.createSpy('MockLikesReto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ParticipacionReto': MockParticipacionReto,
                'User': MockUser,
                'Reto': MockReto,
                'LikesReto': MockLikesReto
            };
            createController = function() {
                $injector.get('$controller')("ParticipacionRetoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'doitApp:participacionRetoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
