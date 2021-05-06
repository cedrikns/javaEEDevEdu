<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Поиск архивных данных</title>
    <%@ page isELIgnored="false" %>
</head>
<body>
<h1>Выберите интересующую дату:</h1>
<form method="post" action="${pageContext.request.contextPath}/archive">
    <input type="date" name="date" placeholder="date"
           value='<%=LocalDate.now().minusDays(1)%>'
           min='<%=LocalDate.now().minusYears(1)%>'
           max='<%=LocalDate.now().minusDays(1)%>'
           required><br/>
    <br>
    <input type="submit"/>
</form>
<br>
<a href="/exchange-rates">Вернуться на начальную страницу</a>
</body>
</html>
