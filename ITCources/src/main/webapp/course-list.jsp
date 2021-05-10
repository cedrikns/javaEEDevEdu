<%@ page import="ru.tsedrik.entity.Course" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Список найденных курсов</title>
</head>
<body>
<table border="1">
    <thead>
    <tr>
        <th>Номер</th>
        <th>Название</th>
        <th>Дата начала</th>
        <th>Дата окончания</th>
        <th>Кол-во мест</th>
    </tr>
    </thead>
    <tbody>
    <% List<Course> courses = (List<Course>)request.getAttribute("foundedCourses");
        for(int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);%>
        <tr>
            <td>
                <a href="show?id=<%=course.getId()%>"><%=(i + 1)%></a>
            </td>
            <td>
                <%=course.getCourseType().getDescription()%>
            </td>
            <td>
                <%=course.getBeginDate()%>
            </td>
            <td>
                <%=course.getEndDate()%>
            </td>
            <td>
                <%=course.getMaxStudentsCount()%>
            </td>
        </tr>
    <% } %>
    </tbody>
</table>
</body>
</html>
