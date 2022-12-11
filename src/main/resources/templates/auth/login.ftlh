<#import "../blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center; font-family: 'Devanagari Sangam MN'">Авторизация</h1>
    <hr>
    <form style="text-align: center" action="/login " method="post">
        <div class="form-group">
            <label for="email">
                 <strong>Электронная почта</strong>
            </label>
            <input type="email"  class="form-control <#if RequestParameters.error??>is-invalid</#if>" id="email" name="username" placeholder="Email..">
            <div id="email" class="invalid-feedback">
                Неверныя электронная почта
            </div>
        </div>
        <div class="form-group">
            <label for="password">
                <strong>Пароль</strong>
            </label>
            <input type="password" class="form-control <#if RequestParameters.error??>is-invalid</#if>" id="password" name="password" placeholder="Password..">
            <div id="password" class="invalid-feedback">
                Неверный пароль
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" style="width: 20%; height: 30%;" class="btn btn-dark">Войти</button>
    </form>
    <hr>
    <h5>Нет аккаунта? <a href="/registration">Зарегистрируйся!</a></h5>
</@t.template>