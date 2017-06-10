(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('MensajeController', MensajeController);

    MensajeController.$inject = ['DataUtils', 'Mensaje'];

    function MensajeController(DataUtils, Mensaje) {

        var vm = this;

        vm.mensajes = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        var stompClient = null;
        vm.connect = connect;
        vm.disconnect = disconnect;
        vm.sendMessage = sendMessage;

        function setConnected(connected) {
            $("#connect").prop("disabled", connected);
            $("#disconnect").prop("disabled", !connected);
            if (connected) {
                $("#conversation").show();
            }
            else {
                $("#conversation").hide();
            }
            $("#greetings").html("");
        }

        function connect() {
            vm.conectado = true;
            var socket = new SockJS('/gs-guide-websocket');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                setConnected(true);
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/greetings', function (greeting) {

                    //showGreeting(JSON.parse(greeting.body).content, JSON.parse(greeting.body).persona);
                    console.log(JSON.parse(greeting.body).mensaje);

                    showGreeting(JSON.parse(greeting.body).mensaje, JSON.parse(greeting.body).horaEnvio);
                });
            });
        }

        function disconnect() {
            vm.conectado = false;
            if (stompClient != null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        }

        function sendMessage() {
            console.log("holi");
            console.log(vm.mensaje);
            stompClient.send("/app/sendMsg", {}, (vm.mensaje));
            showGreeting("hola", vm.mensaje);
        }

        function showGreeting(mensaje, hora){
            $("#greetings").append("<tr><td>" + mensaje +": "+hora+"</td></tr>");
        }

    }
})();

/*

 (function() {
 'use strict';

 angular
 .module('doitApp')
 .controller('MensajeController', MensajeController);

 MensajeController.$inject = ['DataUtils', 'Mensaje'];

 function MensajeController(DataUtils, Mensaje) {

 var vm = this;

 vm.mensajes = [];
 vm.openFile = DataUtils.openFile;
 vm.byteSize = DataUtils.byteSize;


var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

vm.connect = connect;

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {

            showGreeting(JSON.parse(greeting.body).content, JSON.parse(greeting.body).persona);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMsg() {
    stompClient.send("/app/sendMsg", {}, JSON.stringify({'mensaje': vm.mensaje}));
}


function showGreeting(name, message) {
    $("#greetings").append("<tr><td>" + name +": "+message+"</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMsg(); });

});


}
})();

 */
