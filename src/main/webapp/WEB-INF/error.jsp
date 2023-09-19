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
                            <button class="header-button">Logout</button>
                        </form>
            </div>
        </div>

        <div id="container">
            <div class="space-between-div"></div>
            <div class="space-between-div"></div>
            <div class="bigCenteredBox" id="error">
                <div class="caption">Something went wrong. Please visit home page.</div>
            </div>
        </div>
    </body>

    </html>