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
            <form action="/create" method="post">
                <div class="mb-3">
                    <label  for="exampleInputNameAccount" class="form-label">Name account</label>
                    <@spring.formInput "form.nameAccount" "class=\"form-control\" id=\"exampleInputNameAccount\" placeholder=\"Name account *\"" "nameAccount"/>
                    <@spring.showErrors "/br"/>
                </div>
                <div class="mb-3">
                    <label  for="exampleInputBalance" class="form-label">Balance</label>
                    <@spring.formInput "form.balance" "class=\"form-control\" id=\"exampleInputBalance\" placeholder=\"Balance\"" "balance"/>
                    <@spring.showErrors "/br"/>
                </div>
                <div class="mb-3">
                    <label  for="exampleInputCurrency" class="form-label">Currency</label>
                    <@spring.formInput "form.currency" "class=\"form-control\" id=\"exampleInputCurrency\" placeholder=\"Currency *\"" "currency"/>
                    <@spring.showErrors "/br"/>
                </div><br><br>
                <div style="text-align: center;">
                    <button type="submit" class="btn btn-danger text-white">Submit</button><br><br><br>
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