<%@ taglib prefix="c" uri="http://www.springframework.org/security/tags" %>
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div  align="center">
    <nav class="nav-tabs">
        <a class="navbar-brand"  href="/">Home</a> |
        <a class="navbar-brand" href="signup">SignUp</a> |
        <a class="navbar-brand" href="login">Login</a> |
        <c:authorize access="hasRole('ROLE_ADMIN')">
        <a class="navbar-brand" href="admin">Admin</a> |
        </c:authorize>
    </nav>
</div>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>