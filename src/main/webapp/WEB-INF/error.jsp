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
                        <form method="get" action="./logout">
                            <button class="header-button">Logout</button>
                        </form>
            </div>
        </div>

        <div id="container">
            <div class="space-between-div"></div>
            <div class="space-between-div"></div>
            <div class="bigCenteredBox" id="errorBox">
                <div class="inputBox">
                </div>
            </div>
        </div>
    </body>

    </html>