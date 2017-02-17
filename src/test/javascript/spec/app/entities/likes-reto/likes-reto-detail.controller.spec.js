'use strict';

describe('Controller Tests', function() {

    describe('LikesReto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLikesReto, MockUser, MockParticipacionReto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLikesReto = jasmine.createSpy('MockLikesReto');
            MockUser = jasmine.createSpy('MockUser');
            MockParticipacionReto = jasmine.createSpy('MockParticipacionReto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LikesReto': MockLikesReto,
                'User': MockUser,
                'ParticipacionReto': MockParticipacionReto
            };
            createController = function() {
                $injector.get('$controller')("LikesRetoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'doitApp:likesRetoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
