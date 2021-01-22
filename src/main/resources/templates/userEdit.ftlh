<#import "parts/common.ftlh" as c>

<@c.page>
    <html>
    <head>
        <meta name="viewport"
              content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
        <title>24NETWORK</title>
        <link rel="stylesheet" href="/main.css" />
    </head>
    <body background="/static/gradient.png"
          style="background-position: center; background-repeat: no-repeat; background-size: cover;">
    <div>
        <form action="/user" method="post">
            <input type="text" name="username" value="${user.username}">
            <#list roles as role>
                <div>
                    <label><input type="checkbox"
                                  name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
                </div>
            </#list>
            <input type="hidden" value="${user.id}" name="userId">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button type="submit">Save</button>

        </form>
        <div></div>
        <br>
        <form action="/user/block" method="post">
            <input type="hidden" value="${user.id}" name="userId">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button type="submit">Block User</button>
        </form>
        <div></div>
        <br>
        <form action="/user/unblock" method="post">
            <input type="hidden" value="${user.id}" name="userId">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button type="submit">Unblock User</button>
        </form>
    </div>
    </body>
    </html>
</@c.page>