<#import "bloks/common.ftlh" as c>
<@c.page true>
    <h1 style="text-align: center">Товары со всей России</h1>
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
            <h1 style="text-align: center">Не найдено</h1>
        </#list>
    </div>
    <hr>
    <form action="/product/create" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="productName">Название товара</label>
            <input type="text" class="form-control" id="productName" name="name">
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
            <label for="author">Имя продавца</label>
            <input type="text" class="form-control" id="author" name="author">
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
        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <button type="submit" style="width: 100%" class="btn btn-outline-dark">Добавить</button>
    </form>
</@c.page>