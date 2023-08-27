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
                <form method="get" action="./home">
                    <button class="header-button">Home</button>
                </form>
            </div>
        </div>


        <div id="container">
            <div class="space-between-div"></div>
            <div class="space-between-div"></div>
            <div class="bigCenteredBox">
                <div class="inputBox">
                    <c:choose>
                        <c:when test="${isUserSessionActive}">
                            <div class="weather-info">
                                <h3>You are already logged in!</h3>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <form method="post" action="./login">
                                <label for="inputLogin" class="input-label">Login</label>
                                <input type="text" id="inputLogin" name="login">
                                <span class="error" name="errorName">${errors.login}</span>
                                <div class="space-between-div"></div>
                                <label for="inputPassword" class="input-label">Password</label>
                                <input type="text" id="inputPassword" name="password">
                                <span class="error" name="errorName">${errors.password}</span>
                                <div class="space-between-div"></div>
                                <span class="error" name="errorName">${errors.noSuchUser}</span>
                                <div class="space-between-div"></div>
                                <button type="submit">Sign in</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </body>

    </html>