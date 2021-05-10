<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Запись на курс</title>
    <%@ page isELIgnored="false" %>
</head>
<body>
<h1>Запись на курс:</h1>
<form action="enroll-confirm">
    Введите электронный адрес участника
    </br></br>
    <input type="hidden" name="id" value="${courseId}" />
    <input type="text" name="studentEmail"/>
    </br></br>
    <input type="submit" name="enroll" value="Записать">
</form>
</body>
</html>
