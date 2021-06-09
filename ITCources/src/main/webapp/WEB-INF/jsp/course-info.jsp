<%@ page import="ru.tsedrik.entity.Course" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Информация о курсе</title>
</head>
<body>
<h1>${message}</h1>
<% Course course = (Course) request.getAttribute("course"); %>
<form>
    Курс: <%=course.getCourseType().getDescription()%>
    </br></br>
    Дата начала: <%=course.getBeginDate()%>
    </br></br>
    Дата окончания: <%=course.getEndDate()%>
    </br></br>
    Кол-во мест: <%=course.getMaxStudentsCount()%>
    </br></br>
    <a href="${pageContext.request.contextPath}/enroll/<%=course.getId()%>">Запись</a>
</form>
</body>
</html>
