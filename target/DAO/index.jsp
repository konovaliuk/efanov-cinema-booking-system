<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cinema</title>
    <!-- Подключение стилей -->
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<header>
    <c:if test="${empty sessionScope.user}">
        <jsp:include page="/html/navbarnotloginned.jsp"/>
    </c:if>
    <c:if test="${not empty sessionScope.user}">
        <jsp:include page="/html/navbarloginned.jsp"/>
    </c:if>
</header>
</body>
</html>
