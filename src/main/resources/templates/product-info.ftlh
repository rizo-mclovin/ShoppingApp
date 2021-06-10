<#import "bloks/common.ftlh" as c>
<@c.page false>
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <#list images as image>
                <li data-target="#myCarousel" data-slide-to="0" class=""></li>
            </#list>
        </ol>
        <div class="carousel-inner">
            <#list images as image>
                <div class="carousel-item <#if image.previewImage == 1>active</#if>">
                    <svg class="bd-placeholder-img" width="100%" height="100%" xmlns="http://www.w3.org/2000/svg"
                         role="img" aria-label=" :  " preserveAspectRatio="xMidYMid slice" focusable="false">
                        <title></title>
                        <rect width="100%" height="100%" fill="#777"></rect>
                        <text x="50%" y="50%" fill="#777" dy=".3em"></text>
                    </svg>

                    <div class="container">
                        <div class="carousel-caption">
                            <a href="/image/${image.id}">
                                <img class="product" style="height: 100px" src="/image/${image.id}">
                            </a>
                        </div>
                    </div>
                </div>
            </#list>

        </div>
        <a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
    <hr>
    <p><h1>${product.name} | ${product.price}</h1></p>
    <p>${product.description}</p>
    <i>${product.user.nikName}</i>

    <hr>
    <h1 style="text-align: center">Отзывы к товару</h1>
    <hr>
    <form action="/review/create" method="post">
        <input type="hidden" value="${product.id}" name="productId">
        <div class="form-group">
            <label for="name">Ваше имя</label>
            <input type="text" class="form-control" id="name" name="name">
        </div>
        <div class="form-group">
            <label for="rating">Оцените по 10 бальной шкале</label>
            <input type="number" class="form-control" id="rating" name="rating">
        </div>
        <div class="form-group">
            <label for="text">Ваш отзыв</label>
            <input type="text" class="form-control" id="text" name="text">
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <button type="submit" style="width: 100%" class="btn btn-outline-dark">Добавить</button>
    </form>
    <hr>
    <div class="card-columns">
        <#list reviews as review>
            <div class="card text-white bg-dark my-3 product" style="max-width: 100%;">
                <div class="card-body">
                    <h5 class="card-title">${review.text}</h5>
                    <p class="card-text <#if review.rating < 5>text-danger</#if>">${review.rating}</p>
                </div>
                <div class="card-footer">
                    ${review.name}
                </div>
            </div>
        <#else>
            <p style="text-align: center"><h4>Отзывов к данному товару нет</h4></p>
        </#list>
    </div>
</@c.page>