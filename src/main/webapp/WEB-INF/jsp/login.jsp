<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="navigation.jsp"/>
<div class="container">
    <h2>Login</h2>
    <%--@elvariable id="authLoginDTO" type="ru.kinoposisk.dto.auth.AuthLoginDTO"--%>
    <form:form modelAttribute="authLoginDTO" method="post" action="login">
        <div class="form-group">
            <form:label path="login">Login:</form:label>
            <form:input path="login" class="form-control" placeholder="Enter your login"/>
            <form:errors path="login" cssClass="text-danger"/>
        </div>
        <div class="form-group">
            <form:label path="password">Password:</form:label>
            <form:password path="password" class="form-control" placeholder="Enter your password"/>
            <form:errors path="password" cssClass="text-danger"/>
        </div>
        <div class="form-group">
            <input type="submit" value="Login" class="btn btn-primary"/>
        </div>
    </form:form>
</div>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
