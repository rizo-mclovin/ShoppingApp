<#import "bloks/common.ftlh" as c>
<@c.page false>
    <h1 style="text-align: center">Личный кабинет</h1>
    <hr>
    <br>
    <div style="text-align: center">
        <img src="<#if avatar??>/image/${avatar.id}<#else>/images/avatar.png</#if>" style="border:2px solid black; border-radius: 20px" height="120">
        <br>
        <br>
        <h2>${user.nikName}</h2>
        <br>
        Электоронная почта: <b>${user.email}</b>
        <br>
        Номер телефона: <b>${user.numberPhone}</b>
        <br>
        <a class="btn btn-outline-dark mt-3 mb-3" style="text-align: center; width: 100%"
           data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="true" aria-controls="collapseExample">
            Изменить профиль
        </a>
        <div class="collapse mb-3" id="collapseExample" style="">
            <form action="/product/create" method="post" enctype="multipart/form-data">

                <div class="form-group">
                    <label for="image3">Третья фотография к товару</label>
                    <input type="file" class="form-control-file" id="image3" name="file3">
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                <button type="submit" style="width: 100%" class="btn btn-outline-dark">Добавить</button>
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