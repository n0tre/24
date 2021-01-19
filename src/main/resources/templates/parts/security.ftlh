<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
    known2 = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isBlocked = user.isBlocked()
    isAdmin = user.isAdmin()
        isActive = user.isActive()
    >
<#else>
    <#assign
        name = "unknown"
        isBlocked = false
        isAdmin = false
        isActive = false
    >

</#if>




