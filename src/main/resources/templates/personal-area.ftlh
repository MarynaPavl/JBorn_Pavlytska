<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
    <#--    <link href="/bootstrap-5.0.2-dist/css/bootstrap.min.css" rel="stylesheet">-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-success text-white">
    <div class="container-fluid">
        <a class="navbar-brand" href="/personal-area"><h2>MyMoNey</h2></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a href="/accountInfo" class="nav-link" aria-current="page">Account</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/transaction">Transaction</a>
                </li>
                <li class="nav-item">
                    <a href="/type" class="nav-link" aria-current="page">Type</a>
                </li>
            </ul>
            <form action="/login">
                <div style="text-align: right;">
                    <a href="/logout" class="btn btn-danger">Exit</a>
                </div>
            </form>

        </div>
    </div>
</nav>
<div class="container">
    <div style="text-align: center;" class="row">
        <h1>Hello, ${name} ${surname}!</h1><br>
        <h1>Id: ${userId}</h1>
    </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>