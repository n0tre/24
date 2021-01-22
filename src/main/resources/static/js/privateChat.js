'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var chatListForm = document.querySelector('#chatListForm')

var stompClient = null;
var senderId = null;
var senderName = null;
var receiverId = null;
var chatId = null;
var chatMessages = null;
var socket = null;

function provide(event) {
    clearChat();
    const request = new XMLHttpRequest();
    var data = new FormData();
    var v = document.querySelector('#_csrf').value.trim()
    data.append('receiverId', document.querySelector('#receiverId').value.trim());

    request.open("POST", "/privateChat", true);

    request.setRequestHeader("X-CSRF-TOKEN", v);
    request.onload = function () {
        if (request.readyState === request.DONE) {
            if (request.status === 200) {
                var chat = JSON.parse(request.responseText);
                chatId = chat.chatId

                const requestChatMessages = new XMLHttpRequest();
                var d = new FormData();
                d.append('chatId', chatId);

                requestChatMessages.open("POST", "/privateChatMessages", true);

                requestChatMessages.setRequestHeader("X-CSRF-TOKEN", v);
                requestChatMessages.onload = function () {
                    if (requestChatMessages.readyState === request.DONE) {
                        if (requestChatMessages.status === 200) {
                            chatMessages = JSON.parse(requestChatMessages.responseText);
                            getChatMessages();
                            connect(event);
                        }
                    }
                };
                requestChatMessages.send(d);
            }
        }
    };
    request.send(data);

    event.preventDefault();
}

function clearChat() {
    $('.chat-message').remove();
    $('.event-message').remove();
    if (stompClient != null) {
        stompClient.unsubscribe();
        stompClient.disconnect();
        socket.close();
        socket = null;
        stompClient = null;
    }
}

function connect(event) {
    receiverId = document.querySelector('#receiverId').value.trim();
    senderId = document.querySelector('#senderId').value.trim();
    senderName = document.querySelector('#senderName').value.trim();
    if (stompClient == null) {
        socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Special User Topic
    connectingElement.classList.remove('hidden');
    stompClient.subscribe("/topic/" + chatId + "/messages", onMessageReceived);

    connectingElement.classList.add('hidden');
    stompClient.send("/app/chatRegister",
        {},
        JSON.stringify({sender: senderName, chat: chatId, type: 'JOIN'})
    )
}

function getChatMessages() {
    if (chatMessages.length !== 0) {
        jQuery.each(chatMessages, function (i, message) {
            addMessageElement(message);
        });
    }
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function send(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var chatMessage = {
            sender: senderName,
            receiver: receiverId,
            chat: chatId,
            content: messageInput.value,
            type: 'CHAT'
        };

        stompClient.send("/app/chatSend", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    addMessageElement(message);
}

function addMessageElement(message) {
    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = '#ffc107';

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

usernameForm.addEventListener('submit', provide, true)
messageForm.addEventListener('submit', send, true)