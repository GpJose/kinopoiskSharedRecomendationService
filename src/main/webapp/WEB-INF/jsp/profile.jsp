<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="navigation.jsp"/>
<div class="container">
    <h2>Profile</h2>
    <form:form modelAttribute="friendLoginForm" method="post" action="/profile/addFriends">
        <div class="input-group mb-3">
            <form:label path="friendLogin">Send Friend Request:</form:label>
            <form:input path="friendLogin" class="form-control"/>
            <form:errors path="friendLogin" cssClass="alert-warning"/>
            <div class="input-group-append">
                <button type="submit" class="btn btn-primary">Submit</button>
            </div>
        </div>
    </form:form>

    <table class="table">
        <thead>
        <tr>
            <th>Friend</th>
            <th>Movies</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                <c:forEach items="${listFriends}" var="friends">${friends.friendLogin}</c:forEach>
            </td>
            <td>${listMovieHistory}</td>
        </tr>
        </tbody>
    </table>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
