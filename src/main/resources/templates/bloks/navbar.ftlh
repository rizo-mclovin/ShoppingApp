<#include "security.ftlh"/>
<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
    <a class="navbar-brand" href="/">Waikan</a>

    <div class="navbar-collapse collapse" id="navbarsExample03" style="">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="#">Link</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="dropdown03" data-toggle="dropdown" aria-haspopup="true"
                   aria-expanded="false">Город</a>
                <div class="dropdown-menu" aria-labelledby="dropdown03">
                    <a class="dropdown-item" href="#">Action</a>
                    <a class="dropdown-item" href="#">Another action</a>
                    <a class="dropdown-item" href="#">Something else here</a>
                </div>
            </li>
        </ul>
        <#if user??>
            <div class="dropdown text-end">
                <a href="/profile">
                    <img src="/image/1" alt="mdo" width="32" height="32" class="rounded-circle">
                </a>
            </div>
        <#else>
            <button class="btn btn-outline-light btn-sm my-2 my-sm-0" type="button" onclick="window.location.href = '/login'">Войти
            </button>
        </#if>


    </div>
</nav>