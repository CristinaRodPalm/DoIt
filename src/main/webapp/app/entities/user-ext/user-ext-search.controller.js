(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('UserExtSearchController',UserExtSearchController);

    UserExtSearchController.$inject = ['$resource', 'UserExt'];

    function UserExtSearchController ($resource, UserExt) {
        var resourceUrl = "api/search/users/:criteria";

        return  $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get':{
                method: 'GET',
                transformResponse: function (data){
                    if(data){
                        data= angular.fromJson(data);
                    }
                    return data;
                }
            }
        })
    }
})();
