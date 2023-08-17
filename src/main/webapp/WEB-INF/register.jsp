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
            <div id="bigCenteredBox">
                <div class="inputBox">
                    <c:choose>
                        <c:when test="${isUserLogged}">
                            <h3>You have to logout before register!</h3>
                        </c:when>
                        <c:otherwise>
                            <form method="post" action="./register">
                                <label for="inputLogin" class="input-label">Login</label>
                                <input type="text" id="inputLogin" name="login">
                                <span class="error" name="errorName">${errors.login}</span>
                                <div class="space-between-div"></div>
                                <label for="inputPassword" class="input-label">Password</label>
                                <input type="text" id="inputPassword" name="password">
                                <span class="error" name="errorName">${errors.password}</span>
                                <div class="space-between-div"></div>
                                <label for="inputName" class="input-label">Name</label>
                                <input type="text" id="inputName" name="name">
                                <span class="error" name="errorName">${errors.name}</span>
                                <span class="error" name="errorName">${errors.logout}</span>
                                <span class="error" name="errorName">${errors.userexist}</span>
                                <div class="space-between-div"></div>
                                <button type="submit">Register</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </body>

    </html>