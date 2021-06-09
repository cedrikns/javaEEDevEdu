<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ExchangeRates</title>
    <meta charset="UTF-8">
    <%@ page isELIgnored="false" %>
</head>
<body>
<h1>IT курсы</h1>
<h2>Добро пожаловать на сервис записи на IT курсы.</h2>
<h3>Выберите подходящий для вас раздел:</h3>
<ul>
    <li><a href="search">Поиск курса</a></li>
    <sec:authorize access="hasRole('ADMIN')">
        <li><a href="create">Создание курса</a></li>
    </sec:authorize>
</ul>
    <form:form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="submit" value="Выйти">
    </form:form>
</body>
</html>
