<%@ page import="ru.tsedrik.entity.Rate" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Курсы валют на сегодняшний день</title>
    <meta charset="UTF-8">
    <%@ page isELIgnored="false" %>
</head>
<body>
<h2>Данные по курсу валют на сегодняшний день ${actualDate}:</h2>
<table>
    <tr>
        <th>Валюта</th>
        <th>Курс</th>
    </tr>
    <% List<Rate> rates = (List<Rate>)request.getAttribute("actualRates");
        for(Rate rate : rates) { %>
    <tr>
        <td><%=rate.getName().name()%></td>
        <td><%=String.format("%.2f", rate.getRate())%></td>
    </tr>
    <br>
    <% } %>
</table>
<br>
<a href="/exchange-rates">Вернуться на начальную страницу</a>

</body>
</html>
