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
                    <a class="nav-link" aria-current="page" href="/accountInfo">Account</a>
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
  </nav><br><br>
    <div>
      <form action="/createTransaction">
        <div style="text-align: center;">
          <a href="/createTransaction" class="btn btn-danger btn-lg">Create new transaction</a>
        </div>
      </form>
    </div><br><br>

  <div class="container">
      <div class="p-3 mb-2">
          <div class="row">
              <div class="col">
                  <form action="/transaction" method="post">
                    <div>
                        <label for="exampleInputAssignment" class="form-label">Assignment</label>
                        <@spring.formInput "form.assignment" "class=\"form-control\" id=\"exampleInputAssignment\" placeholder=\"Assignment *\"" "assignment"/>
                        <@spring.showErrors "/br"/>
                    </div>
                    <div class="col">
                        <label for="exampleInputFromDate" class="form-label">From date</label>
                        <@spring.formInput "form.fromDate" "class=\"form-control\" id=\"exampleInputFromDate\" placeholder=\"yyyy-mm-dd\"" "fromDate"/>
                        <@spring.showErrors "/br"/>
                    </div>
                    <div class="col">
                        <label for="exampleInputToData" class="form-label">To Data</label>
                        <@spring.formInput "form.toData" "class=\"form-control\" id=\"exampleInputToData\" placeholder=\"yyyy-mm-dd\"" "toData"/>
                        <@spring.showErrors "/br"/>
                    </div><br><br>
                          <div>
                              <form action="/transaction">
                                  <div style="text-align:center;">
                                      <button type="submit" class="btn btn-danger btn-lg">Submit</button>
                                  </div>
                              </form>
                          </div>

                      <div class="flex-lg-column">
                          <#list (types)! as type>
                              <h3>Search for: ${(lastSearch)!} </h3> <h5>from ${dataFrom} to ${dataTo}</h5>
                              ${type}
                              <div id="line-block">
                              <form action="/deleteTransaction${type.id}" method="post">
                                  <input type="hidden" name="deleteTransaction" value="DELETE"/>
                                  <button type="submit" multiple name="id" class="btn btn-danger text-white"
                                          value=${type.id}>Delete transaction
                                  </button>
                              </form>
                          </div>
                          </#list>


                      </div>
                  </form>
              </div>
          </div>
      </div>
  </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" rel="script"></script>
  </body>
</html>