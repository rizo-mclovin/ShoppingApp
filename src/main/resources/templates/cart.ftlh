<#import "bloks/common.ftlh" as c>
<@c.page true products>
    <div class="card-columns">
        <#list products as product>
            <div class="card-body">
                <h5 class="card-title">${product.id}</h5>
            </div>
        <#else>
            <h1 style="text-align: center">Не найдено</h1>
        </#list>
    </div>
</@c.page>