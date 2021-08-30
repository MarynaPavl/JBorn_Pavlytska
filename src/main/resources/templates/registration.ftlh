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
<div class="container">
    <div class="p-3 mb-2 bg-success text-white">
      <div class="row">
         <div class="col"></div>
         <div class="col" >
             <h1 style="text-align: center" class="text-dark fw-bold">MyMoNey</h1>
            <form action="/registration" method="post">
                <div class="mb-3">
                    <label  for="exampleInputFirstName" class="form-label">First Name</label>
                    <@spring.formInput "form.firstName" "class=\"form-control\" id=\"exampleInputFirstName\" placeholder=\"First Name *\"" "firstName"/>
                    <@spring.showErrors "/br"/>
                </div>
                <div class="mb-3">
                    <label  for="exampleInputLastName" class="form-label">Last Name</label>
                    <@spring.formInput "form.lastName" "class=\"form-control\" id=\"exampleInputLastName\" placeholder=\"Last Name\"" "lastName"/>
                    <@spring.showErrors "/br"/>
                </div>
                <div class="mb-3">
                    <label  for="exampleInputEmail" class="form-label">Email address</label>
                    <@spring.formInput "form.email" "class=\"form-control\" id=\"exampleInputEmail\" placeholder=\"Enter email *\"" "email"/>
                    <@spring.showErrors "/br"/>
                </div>
                <div class="mb-3">
                    <label for="exampleInputPassword" class="form-label">Password</label>
                    <@spring.formInput "form.password" "class=\"form-control\" id=\"exampleInputPassword\" placeholder=\"Password *\"" "password"/>
                    <@spring.showErrors "/br"/>
                </div><br><br>
                <div style="text-align: center;">
                    <button type="submit" class="btn btn-danger text-white">Submit</button><br><br><br>
                    <h4><a href="/login-form" class="text-white">Log in</a></h4>
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