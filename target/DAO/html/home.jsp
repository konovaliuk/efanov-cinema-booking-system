<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <title>You were registered!</title>
</head>
<body>
<c:if test="${empty sessionScope.user}">
    <jsp:include page="/html/navbarnotloginned.jsp"/>
</c:if>
<c:if test="${not empty sessionScope.user}">
    <jsp:include page="/html/navbarloginned.jsp"/>
</c:if>
<p> Screenings: </p>
<ul>
    <jsp:useBean id="screenings" scope="session" type="java.util.List<com.application.model.Screening>"/>
    <c:forEach var="screening" items="${screenings}">
        <li>
            <c:out value="${screening.movie.title}"/>
            <c:out value="${screening.movie.director}"/>
            <c:out value="${screening.movie.description}"/>
            <c:out value="${screening.movie.durationTime}"/>
            <c:out value="${screening.screeningStart}"/>
        </li>
    </c:forEach>
</ul>
</body>
</html>
