<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <jsp:include page="navigation.jsp"/>
    <h1>Admin</h1>
    <table>
        <c:forEach items="${allUsers}" var="User">
            <tr>
                <td>${User.login}</td>
                <td>${User.email}</td>
                <td>
                    <c:forEach items="${User.roles}" var="role">${role}; </c:forEach>
                </td>
                <td>
                    <c:forEach items="${User.friends}" var="friend">${friend.date}; </c:forEach>
                </td>
                <td>
                    <c:forEach items="${User.friends}" var="friend">${friend.friendRequestStatus} </c:forEach>
                </td>
                <td>
                    <c:forEach items="${User.movieHistoriesList}" var="history">${history.movie}; </c:forEach>
                </td>
                <td>
                        ${User.active}
                </td>
                <td>
                    <form:form modelAttribute="friendLoginForm" method="post" action="/admin/userStatus">
                        <form:hidden path="id" value="${User.id}" />
                        <form:hidden path="login" value="${User.login}"/>
                        <form:button>Submit</form:button>
                    </form:form>
                </td>
            </tr>
        </c:forEach>
    </table>

</div>
</div>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
