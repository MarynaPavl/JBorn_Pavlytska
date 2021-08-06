<#import "/spring.ftl" as spring />
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-success text-white">
    <div class="container-fluid">
        <a class="navbar-brand" href="/index"><h2>MyMoNey</h2></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/transaction">Transaction</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="type">Type</a>
                </li>
            </ul>
            <form action="/login">
                <div style="text-align: right;">
                    <a href="/login" class="btn btn-danger">Exit</a>
                </div>
            </form>
        </div>
    </div>
</nav>
<div class="container">
    <h1> Your account:</h1><br/>
    <div class="flex-lg-column">
        <#list items as item>
            <li id="item_block">${item}
                <div id="line-block">
                    <form action="/delete${item.id}" method="post">
                        <input type="hidden" name="deleteAccount" value="DELETE"/>
                        <button type="submit" multiple name="id" class="btn btn-danger text-white"
                                value=${item.id}>Delete account
                        </button>
                    </form>
                </div>
            </li>
        </#list>
    </div>
    <br><br>

    <form action="/create">
        <button type="submit" class="btn btn-danger text-white">Create new account</button>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" rel="script"></script>
</body>
</html>