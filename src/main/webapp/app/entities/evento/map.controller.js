var app = angular.module('doitApp', ['NgMap']);
app.controller('mapTestController', function(NgMap) {
    NgMap.getMap().then(function(map) {
        console.log(map.getCenter());
        console.log('markers', map.markers);
        console.log('shapes', map.shapes);
    });
});
