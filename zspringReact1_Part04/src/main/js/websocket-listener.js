'use strict';

//...1. You pull in the SockJS JavaScript library for talking over WebSockets.
var SockJS = require('sockjs-client');

//...2. You pull in the stomp-websocket JavaScript library to use the STOMP sub-protocol.
require('stompjs'); 

function register(registrations) {
//...3. Here is where the WebSocket is pointed at the application’s /payroll endpoint.	
	var socket = SockJS('/payroll'); 
	
	var stompClient = Stomp.over(socket);
	
	stompClient.connect({}, function(frame) {
		
//...4. Iterate over the array of registrations supplied so each can subscribe for callback 
//      as messages arrive.
//      Each registration entry has a route and a callback.
		registrations.forEach(function (registration) { 
			stompClient.subscribe(registration.route, registration.callback);
		});
	});
}

module.exports.register = register;

