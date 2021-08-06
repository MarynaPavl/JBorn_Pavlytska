<#import "/spring.ftl" as spring />
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="p-3 mb-2 ">
        <div class="row">
            <div class="col"></div>
            <div class="col" >
                <form action="/createTransaction" method="post">
                    <div class="mb-3">
                        <label  for="exampleInputAccountFrom" class="form-label">Account from</label>
                        <@spring.formInput "form.accountFrom" "class=\"form-control\" id=\"exampleInputAccountFrom\" placeholder=\"Account from or 0\"" "accountFrom"/>
                        <@spring.showErrors "/br"/>
                    </div>
                    <div class="mb-3">
                        <label  for="exampleInputAccountTo" class="form-label">Account To</label>
                        <@spring.formInput "form.accountTo" "class=\"form-control\" id=\"exampleInputAccountTo\" placeholder=\"Account to or 0\"" "accountTo"/>
                        <@spring.showErrors "/br"/>
                    </div>
                    <div class="mb-3">
                        <label  for="exampleInputSum" class="form-label">Sum</label>
                        <@spring.formInput "form.sum" "class=\"form-control\" id=\"exampleInputSum\" placeholder=\"Sum *\"" "sum"/>
                        <@spring.showErrors "/br"/>
                    </div>
                    <div class="mb-3">
                        <label  for="exampleInputData" class="form-label">Data</label>
                        <@spring.formInput "form.data" "class=\"form-control\" id=\"exampleInputData\" placeholder=\" in format: yyyy-mm-dd *\"" "data"/>
                        <@spring.showErrors "/br"/>
                    </div>
                    <div class="mb-3">
                        <label  for="exampleInputAssignmentId" class="form-label">Assignment Id</label>
                        <@spring.formInput "form.assignmentId" "class=\"form-control\" id=\"exampleInputAssignmentId\" placeholder=\"assignment Id one or more*\"" "assignmentId"/>
                        <@spring.showErrors "/br"/>
                    </div><br>
                    <div style="text-align: center;">
                        <button type="submit" class="btn btn-danger text-white">Submit</button><br><br><br>
                          <h3>${(successfully)!}</h3>
                    </div><br>
                    <h5 style="text-align: center"><a href="/transaction" class="text-success">Transaction</a></h5>

                </form>
            </div>
            <div class="col"></div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" rel="script"></script>
</body>
</html>