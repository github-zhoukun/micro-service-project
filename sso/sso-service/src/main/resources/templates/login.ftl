<#assign path=request.contextPath >
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登入</title>
</head>
<body>
<h1>----请登入----</h1>
<form action="${path}/login" method="get">
    username : <input type="text" name="username"><br>
    Password : <input type="password" name="password"><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>