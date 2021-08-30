<#import "/spring.ftl" as spring />
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
                    <a class="nav-link" aria-current="page" href="/personal-area">Home</a>
                </li>
                <li class="nav-item">
                    <a href="/accountInfo" class="nav-link" aria-current="page">Account</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/transaction">Transaction</a>
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
<div class="container" style="text-align: center">
    <div class="p-3 mb-2">
        <div class="row">
            <div class="col">
                <form action="/type" method="post">
                    <div class="mb-3">
                        <label for="exampleInputString" class="form-label">Search</label>
                        <@spring.formInput "form.string" "class=\"form-control\" id=\"exampleInputString\" placeholder=\"Search *\"" "string"/>
                        <@spring.showErrors "/br"/>
                    </div>
                    <br><br>
                    <div style="text-align: center;">
                        <button type="submit" class="btn btn-danger text-white">Submit</button>
                        <br>
                        <h5><a href="/createType" class="text-success">Create type</a></h5>
                    </div>
                    <div class="flex-lg-column">
                        <#list (types)! as type>
                            <h3> Search for: ${(lastSearch)!}</h3><br/>
                                ${type}
                                <form action="/type/deleteType${type.id}" method="POST">
                                    <input type="hidden" name="deleteType" value="DELETE"/>
                                    <button type="submit" multiple name="id" class="btn btn-danger text-white"
                                            value="Delete">Delete type
                                    </button>
                                </form>
                        </#list>
                    </div>
                </form>
            </div>
            <div class="col"></div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" rel="script"></script>
</body>
</html>