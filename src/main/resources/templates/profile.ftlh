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
        <a class="btn btn-outline-dark mt-3 mb-3" style="text-align: center"
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
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-outline-danger mb-2">
                Выйти из аккаунта
            </button>
         </form>

    </div>
    <hr>
    <hr>
    <div class="card-columns">
        <#list products as product>
            <a href="/product/${product.id}" class="product-link">
                <div class="card text-white bg-dark my-3 product">
                    <img src="/image/${product.previewImageId}"
                         class="card-img-top">
                    <div class="card-body">
                        <h5 class="card-title">${product.name}</h5>
                        <p class="card-text">${product.price}</p>
                    </div>
                    <div class="card-footer">
                        ${product.user.nikName}
                    </div>
                </div>
            </a>
        <#else>
            <h1 style="text-align: center"></h1>
        </#list>
    </div>
    <hr>
    <a class="btn btn-outline-dark mt-2 mb-4" style="width: 100%" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="true" aria-controls="collapseExample">
        Добавить новый товар
    </a>
    <div class="collapse show" id="collapseExample" style="">
        <form action="/product/create" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="productName">Название товара</label>
                <input type="text" class="form-control <#if nameError??>is-invalid</#if>"
                       id="productName" name="name">
                <#if nameError??>
                    <div id="productName" class="invalid-feedback">
                        ${nameError?if_exists}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <label for="price">Цена товара</label>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" id="price" name="price">
                    <div class="input-group-append">
                        <span class="input-group-text">рублей</span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="description">Описание товара</label>
                <input type="text" class="form-control" id="description" name="description">
            </div>
            <div class="form-group">
                <label for="image1">Первая фотография к товару</label>
                <input type="file" class="form-control-file" id="image1" name="file1">
            </div>
            <div class="form-group">
                <label for="image2">Вторая фотография к товару</label>
                <input type="file" class="form-control-file" id="image2" name="file2">
            </div>
            <div class="form-group">
                <label for="image3">Третья фотография к товару</label>
                <input type="file" class="form-control-file" id="image3" name="file3">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

            <button type="submit" style="width: 100%" class="btn btn-outline-dark">Добавить</button>
        </form>
    </div>
</@c.page>