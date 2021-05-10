<%@ page import="ru.tsedrik.entity.CourseType" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Поиск курсов</title>
    <%@ page isELIgnored="false" %>
</head>
<body>
<h1>Выберите интересующий курс:</h1>
<form action="${pageContext.request.contextPath}/courses-list">
    <select name = "courseType">
        <% List<CourseType> types = (List<CourseType>)request.getAttribute("courseTypes");
            for (CourseType type : types){ %>
        <option value="<%=type.toString()%>"><%=type.getDescription()%></option>
        <% } %>
    </select>
    <br/><br/>
    <input type="submit" name="submit" value="Поиск"/>
</form>
<br>
<a href="/it-courses">Вернуться на начальную страницу</a>
</body>
</html>
