<#import "bloks/common.ftlh" as c>
<@c.page false>
    <h1 style="text-align: center">Активация аккаунта</h1>
    <hr>
    <h3 style="text-align: center">
        <#if activate>
            Поздравляю! Твоя электронная почта
            была успешно подтверждена.
            <br>
            <a href="/login">Теперь ты можешь авторизироваться</a>
            <#else>
            Ключ не был найден! Ошибка подтверждения электронной почты.
        </#if>
    </h3>
</@c.page>