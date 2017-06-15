(function() {
    'use strict';

    angular
        .module('doitApp')
        .controller('ChatTemplateController', ChatTemplateController);

    ChatTemplateController.$inject = ['DataUtils', 'Mensaje', '$state', 'Chat', 'UserExt'];

    function ChatTemplateController(DataUtils, Mensaje, $state, Chat, UserExt) {

        var vm = this;

        vm.chat = $state.params.chat;
        console.log(vm.chat);

        //vm.userExt = Chat.getOne({'id': vm.chat.emisor}, {});
        //console.log(vm.userExt);
/*

        UserExt.getUserExt(function (result) {
            vm.currentUserExt = result;
            console.log(vm.currentUserExt);
        })
*/

        vm.mensajes = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        var stompClient = null;
        vm.connect = connect;
        vm.disconnect = disconnect;
        vm.sendMessage = sendMessage;
        vm.conectado = true;

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
            stompClient.send("/app/sendMsg", {}, (vm.mensaje));
            //showGreeting("hola", vm.mensaje);
        }

        function showGreeting(mensaje, hora){
            $("#greetings").append("<tr><td>" + mensaje +": "+hora+"</td></tr>");
        }

    }
})();
