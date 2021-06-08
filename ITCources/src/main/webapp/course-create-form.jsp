<%@ page import="ru.tsedrik.entity.CourseType" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Создание курса</title>
</head>
<body>
<h1>Заполните данные по новому курсу:</h1>
<form:form action="save-course" modelAttribute="course" method="post">

    Название курса:<br/>
    <form:select name = "courseType" path="courseType">
        <% List<CourseType> types = (List<CourseType>)request.getAttribute("courseTypes");
            for (CourseType type : types){ %>
        <option value="<%=type.toString()%>"><%=type.getDescription()%></option>
        <% } %>
    </form:select>
    <br/><br/>
    Дата начала курса:<br/>
    <form:input path="beginDate" type="date" name="beginDate"
           value='<%=LocalDate.now().plusDays(1)%>'
           min='<%=LocalDate.now().plusDays(1)%>'
           required="required"/>
    <br/><br/>
    Дата окончания курса:<br/>
    <form:input path="endDate" type="date" name="endDate"
           value='<%=LocalDate.now().plusDays(1)%>'
           min='<%=LocalDate.now().plusDays(1)%>'
           required="required"/>
    <br/><br/>
    Максимально кол-во учеников:<br/>
    <form:input path="maxStudentsCount" type="text" name="maxStudentsCount" required="required"/>
    <br/><br/>
    <input type="submit" name="submit" value="Создать"/>
</form:form>

<br>
<a href="/it-courses">Вернуться на начальную страницу</a>
</body>
</html>
