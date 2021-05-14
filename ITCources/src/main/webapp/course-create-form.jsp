<%@ page import="ru.tsedrik.entity.CourseType" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Создание курса</title>
    <%@ page isELIgnored="false" %>
</head>
<body>
<h1>Заполните данные по новому курсу:</h1>
<form action="${pageContext.request.contextPath}/show">

    Название курса:<br/>
    <select name = "courseType">
        <% List<CourseType> types = (List<CourseType>)request.getAttribute("courseTypes");
            for (CourseType type : types){ %>
                <option value="<%=type.toString()%>"><%=type.getDescription()%></option>
        <% } %>
    </select>
    <br/><br/>
    Дата начала курса:<br/>
    <input type="date" name="beginDate"
           value='<%=LocalDate.now().plusDays(1)%>'
           min='<%=LocalDate.now().plusDays(1)%>'
           required>
    <br/><br/>
    Дата окончания курса:<br/>
    <input type="date" name="endDate"
           value='<%=LocalDate.now().plusDays(1)%>'
           min='<%=LocalDate.now().plusDays(1)%>'
           required>
    <br/><br/>
    Максимально кол-во учеников:<br/>
    <input type="text" name="maxStudentsCount" required>
    <br/><br/>
    <input type="submit" name="submit" value="Создать"/>
</form>
<br>
<a href="/it-courses">Вернуться на начальную страницу</a>
</body>
</html>
