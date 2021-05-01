<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ExchangeRates</title>
    <meta charset="UTF-8">
    <%@ page isELIgnored="false" %>
</head>
<body>
<h1>Курсы валют</h1>
<h2>Добро пожаловать на сервис получения информации о курсах валют.</h2>
<h3>Выберите подходящий для вас раздел:</h3>
<ul>
    <li><a href="${pageContext.request.contextPath}/actual">Актуальный курс валют</a></li>
    <li><a href="${pageContext.request.contextPath}/archive">Архивный курс валют</a></li>
</ul>
</body>
</html>
