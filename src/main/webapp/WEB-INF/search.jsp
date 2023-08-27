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
                    <form method="post" action="./search">
                        <label for="cityInput" class="input-label">Enter city name</label>
                        <div class="space-between-div"></div>
                        <input type="text" id="cityInput" name="cityInput" placeholder="e.g. New York">
                        <div class="weather-info"><span class="error" name="errorName">${errorCityName}</span></div>
                        <div class="space-between-div"></div>
                        <button type="submit">Search</button>
                    </form>
                </div>
            </div>
        </div>
    </body>

    </html>