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
                <form action="/createType" method="post">
                    <div class="mb-3">
                        <label  for="exampleInputAssignment" class="form-label">Assignment</label>
                        <@spring.formInput "form.assignment" "class=\"form-control\" id=\"exampleInputAssignment\" placeholder=\"Assignment *\"" "assignment"/>
                        <@spring.showErrors "/br"/>
                    </div>
                   <br><br>
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