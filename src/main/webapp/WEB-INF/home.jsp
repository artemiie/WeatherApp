<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>Weather</title>
        <style>
            <%@include file="/WEB-INF/style.css" %>
        </style>
    </head>

    <body>
        <div class="header">
            <div class="header-title">Weather App</div>
            <div class="header-buttons">
                <c:choose>
                    <c:when test="${isUserLogged}">
                        <form method="get" action="./logout">
                            <button class="header-button">Logout</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form method="get" action="./login">
                            <button class="header-button">Login</button>
                        </form>
                        <form method="get" action="./register">
                            <button class="header-button">Register</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div id="container">
            <div class="space-between-div"></div>
            <div class="space-between-div"></div>
            <div id="bigCenteredBox">
                <div class="inputBox">
                    <c:choose>
                        <c:when test="${isUserLogged}">
                            <form method="get" action="./profile">
                                <button type="submit">My Profile</button>
                            </form>
                            <div class="space-between-div"></div>
                            <form method="get" action="./search">
                                <button type="submit">Search</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form method="get" action="./search">
                                <button type="submit">Search</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </body>

    </html>