<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <div class="p-3 mb-2 bg-success text-white">
    <div class="row">
        <div class="col"></div>
        <div class="col" >
            <h1 style="text-align: center" class="text-dark fw-bold">MyMoNey</h1>
            <form action="/login" method="post">
                <div class="mb-3">
                    <label  for="exampleInputEmail" class="form-label " >Email address</label>
                    <input name="email" class="form-control" id="exampleInputEmail" placeholder="Enter email" type="text"/>
                </div>
                <div class="mb-3">
                    <label for="exampleInputPassword" class="form-label">Password</label>
                    <input name="password" class="form-control" id="exampleInputPassword" placeholder="Password" type="password"/>
                </div><br><br>
                <div style="text-align: center;">
                <button type="submit" class="btn btn-danger text-white">Submit</button><br><br><br>
                    <h5><a href="/registration" class="text-white">Registration</a></h5>
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