
<#import "parts/common.ftlh" as c>


<@c.page>
    <html>
    <head>
        <meta name="viewport"
              content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
        <title>24NETWORK</title>
        <link rel="stylesheet" href="/main.css" />
    </head>
    <body background="../static/gradient.png"
          style="background-position: center; background-repeat: no-repeat; background-size: cover;">
    <noscript>
        <h2>Sorry! Your browser doesn't support Javascript</h2>
    </noscript>
    <div id="username-page">
        <div class="username-page-container">
            <h1 class="title">Type username for chatting</h1>
            <form id="usernameForm" name="usernameForm">
                <div class="form-group">
                    <input type="hidden" id="username" value="${user.username}"/>
                    <select name="usersName" id="name" class="form-control">
                        <#list users as user>
                            <option>${user.username}</option>
                        </#list>
                    </select>
                </div>
                <div class="form-group">
                    <button type="submit" class="accent username-submit">Start
                        Chatting</button>
                </div>
            </form>
        </div>
    </div>

    <div id="chat-page" class="hidden">
        <div class="chat-container">
            <div class="chat-header">
                <h2>24NETWORK Chat</h2>
            </div>
            <div class="connecting">Connecting...</div>
            <ul id="messageArea">

            </ul>
            <form id="messageForm" name="messageForm" nameForm="messageForm">
                <div class="form-group">
                    <div class="input-group clearfix">
                        <input type="text" id="message" placeholder="Type a message..."
                               autocomplete="off" class="form-control" />
                        <button type="submit" class="primary">Send</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <script
            src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
    <script
            src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="/static/js/privateChat.js"></script>
    </body>
    </html>
</@c.page>