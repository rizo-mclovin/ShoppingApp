<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    nikName = user.getNikName()
    >
<#else>
    <#assign
    nikName = ""
    >
</#if>