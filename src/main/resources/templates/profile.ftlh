<#import "bloks/common.ftlh" as c>
<@c.page false>
    <h1 style="text-align: center">Личный кабинет</h1>
    <hr>
    <br>
    <div style="text-align: center">
        <#if avatar??><a href="/image/${avatar.id}"></#if>
            <img src="<#if avatar??>/image/${avatar.id}<#else>/images/avatar.png</#if>"
                 style="border:2px solid black; border-radius: 20px" height="120">
            <#if avatar??></a></#if>
        <br>
        <br>
        <h2>${user.nikName}</h2>
        <br>
        Электоронная почта: <b>${user.email}</b>
        <br>
        Номер телефона: <b>${user.numberPhone}</b>
        <br>
        <a class="btn btn-outline-dark mt-3 mb-3" style="text-align: center; width: 100%"
           data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="true"
           aria-controls="collapseExample">
            Изменить профиль
        </a>
        <div class="collapse mb-3" id="collapseExample" style="">
            <form action="/profile/edit" method="post" enctype="multipart/form-data">
                <input type="hidden" name="email" value="${user.email}"/>
                <div class="form-group">
                    <label for="nikName">Имя пользователя</label>
                    <input type="text" class="form-control" id="nikName" name="nikName" value="${user.nikName}">
                </div>
                <div class="form-group">
                    <label for="phoneNumber">Номер телефона</label>
                    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                           value="${user.numberPhone}">
                </div>
                <div class="form-group">
                    <label for="avatar">Фото профиля</label>
                    <input type="file" class="form-control-file" id="avatar" name="file">
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" style="width: 100%" class="btn btn-outline-dark">Изменить профиль</button>
            </form>
        </div>
        <br>
        <a class="btn btn-outline-dark mb-3" style="text-align: center; width: 100%"
           href="/my/products">
            Мои товары
        </a>
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" style="text-align: center; width: 100%" class="btn btn-outline-danger mb-2">
                Выйти из аккаунта
            </button>
        </form>
    </div>
</@c.page>