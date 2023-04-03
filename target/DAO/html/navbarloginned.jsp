<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/html/home.jsp">Barabaz</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#myNavbar"
                aria-controls="myNavbar" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="/html/home.jsp">Menu</a>
                </li>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item">
                <form method="post" action="logout?command=logout">
                    <button type="submit" class="nav-link">Log out</button>
                </form>
                </li>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>
