<#include "security.ftlh"/>
<nav class="navbar navbar-dark bg-dark">
    <div class="container-fluid">
        <a href="/" class="btn btn-outline-light mr-5">Waikan</a>
        <#if user??>
            <div class="dropdown text-end">
                <a href="/profile">
                    <img src="<#if user.avatar??>/image/${user.avatar.id}<#else>/images/avatar.png</#if>" alt="mdo" width="32" height="32" class="rounded-circle">
                </a>
            </div>
        <#else>
            <button class="btn btn-outline-light my-2 my-sm-0" type="button" onclick="window.location.href = '/login'">
                Войти
            </button>
        </#if>
    </div>
</nav>