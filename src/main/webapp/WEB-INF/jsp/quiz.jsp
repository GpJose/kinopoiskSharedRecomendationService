<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
</head>
<body>
<div class="container">
    <h1>Quiz</h1>
    <%--@elvariable id="quizAnswersDTO" type="ru.kinoposisk.dto.quiz.QuizAnswersDTO"--%>
    <form:form modelAttribute="quizAnswersDTO" method="post" action="/quiz">
        <div class="form-group">
            <form:label path="genre">Genre:</form:label>
            <form:select path="genre" multiple="true" class="form-control genre-select">
                <c:forEach items="${genreEnums}" var="genreEnum">
                    <form:option value="${genreEnum}">${genreEnum}</form:option>
                </c:forEach>
            </form:select>
            <form:errors path="genre" cssClass="alert-warning"/>
        </div>
        <div class="form-group">
            <form:label path="duration">Duration:</form:label>
            <form:input path="duration" class="form-control" />
            <form:errors path="duration" cssClass="alert-warning"/>
        </div>
        <div class="form-group">
            <form:label path="country">Country:</form:label>
            <form:select path="country" multiple="true" class="form-control country-select">
                <c:forEach items="${countryEnums}" var="countryEnum">
                    <form:option value="${countryEnum}">${countryEnum}</form:option>
                </c:forEach>
            </form:select>
            <form:errors path="country" cssClass="alert-warning"/>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form:form>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<script>
    $(document).ready(function() {
        $('.genre-select').select2({
            placeholder: "Select Genres",
            allowClear: true
        });
        $('.country-select').select2({
            placeholder: "Select Countries",
            allowClear: true
        });
    });
</script>
</body>
</html>
