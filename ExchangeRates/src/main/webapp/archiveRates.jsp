<%@ page import="ru.tsedrik.entity.Rate" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Архивные курсы валют</title>
    <meta charset="UTF-8">
    <%@ page isELIgnored="false" %>
</head>
<body>
<% if(request.getAttribute("message") != null){ %>
    ${message}
<% } else { %>
<h3>Ниже указаны данные по курсам валют на ${archiveDate}:</h3>
<table>
    <tr>
        <th>Валюта</th>
        <th>Курс</th>
    </tr>
    <% List<Rate> rates = (List<Rate>)request.getAttribute("archiveRates");
        for(Rate rate : rates) { %>
    <tr>
        <td><%=rate.getName().name()%></td>
        <td><%=String.format("%.2f", rate.getRate())%></td>
    </tr>
    <br>
    <% } %>
</table>
<% } %>
<ul>
    <li><a href="/exchange-rates/archive">Назад</a></li>
    <li><a href="/exchange-rates">Вернуться на начальную страницу</a></li>
</ul>
</body>
</html>
