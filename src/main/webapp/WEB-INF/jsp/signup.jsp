<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="navigation.jsp"/>
<div class="container">

    <h2>Sign Up</h2>
    <%--@elvariable id="authSignUpDTO" type="ru.kinoposisk.dto.auth.AuthSignUpDTO"--%>
    <form:form modelAttribute="authSignUpDTO" method="post" action="signup">
        <div class="form-group" >
            <form:label path="login" >Login:</form:label>
            <form:input path="login" class="form-control" placeholder="Enter your login"/>
            <div class="errors">
                <form:errors path="login" cssClass="alert-warning"/>
            </div>
            <c:if test="${not empty userAlreadyExist}">
                <div class="alert-warning">
                        ${userAlreadyExist}
                </div>
            </c:if>

        </div>

        <div class="form-group">

            <form:label path="password">Password:</form:label>
            <form:password path="password" class="form-control" placeholder="Enter your password"/>
            <div class="errors">
                <form:errors path="password" cssClass="alert-warning"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="email">Email:</form:label>
            <form:input path="email" class="form-control" placeholder="Enter your email"/>
            <div class="errors">
                <form:errors path="email" cssClass="alert-warning"/>
            </div>
        </div>
        <br>
        <div class="form-row">
            <input type="submit" value="Sign Up" class="btn btn-primary"/>
        </div>
    </form:form>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
