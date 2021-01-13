<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
    known2 = Session.SPRING_SECURITY_CONTEXT??
>

<#if known2>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isBlocked = user.isBlocked()
    >
<#else>
    <#assign
    name = "unknown"
    isBlocked = false
    >

</#if>

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isAdmin = user.isAdmin()
    >
    <#else>
    <#assign
        name = "unknown"
        isAdmin = false
    >

</#if>


