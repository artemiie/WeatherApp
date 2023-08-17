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
                <c:choose>
                    <c:when test="${isUserLogged}">
                        <c:choose>
                            <c:when test="${!isSubscriptionActive}">
                                <form method="post" action="./profile">
                                    <input type="hidden" name="lat" value="${lat}">
                                    <input type="hidden" name="lon" value="${lon}">
                                    <input type="hidden" name="city" value="${city}">
                                    <input type="hidden" name="country" value="${country}">
                                    <input type="hidden" name="state" value="${state}">
                                    <button type="submit" class="header-button">Subscribe on location</button>
                                </form>
                            </c:when>
                        </c:choose>
                        <form method="get" action="./profile">
                            <button class="header-button">My Profile</button>
                        </form>
                    </c:when>
                </c:choose>
            </div>
        </div>
        <div id="container">
            <div class="weather-info">
                <h1>${country} ${state} ${city}</h1>
            </div>
            <div class="caption">Current weather</div>
            <div class="weather-box">
                <div class="weather-info"><b>${weather.current.currentDt.dayOfMonth} ${weather.current.currentDt.month}
                        ${weather.current.currentDt.hour}:${weather.current.currentDt.minute}</b></div>
                <div class="weather-info">Sunrise: ${weather.current.sunriseDt.hour}:${weather.current.sunriseDt.minute}
                </div>
                <div class="weather-info">Sunset: ${weather.current.sunsetDt.hour}:${weather.current.sunsetDt.minute}
                </div>
                <div class="weather-info">Temperature: ${weather.current.temperature} &#8451</div>
                <div class="weather-info">Feels like: ${weather.current.feelsLike} &#8451</div>
                <div class="weather-info">Humidity: ${weather.current.humidity} %</div>
                <div class="weather-info">Clouds: ${weather.current.clouds} %</div>
                <div class="weather-info">Wind speed: ${weather.current.windSpeed} m/s</div>
            </div>
            <div class="space-between-div"></div>
            <div class="caption">Weather for next 8 days</div>
            <div id="smallBoxContainer">
                <c:forEach var="dailyWeather" items="${weather.dailyList}">
                    <div class="smallBox">
                        <h2>${dailyWeather.dateTime.dayOfMonth} ${dailyWeather.dateTime.month}</h2>
                        <div class="weather-info">
                            Sunrise:${dailyWeather.sunriseDt.hour}:${dailyWeather.sunriseDt.minute}</div>
                        <div class="weather-info">Sunset: ${dailyWeather.sunsetDt.hour}:${dailyWeather.sunsetDt.minute}
                        </div>
                        <div class="weather-info">Temp</div>
                        <div class="weather-info">day:${dailyWeather.temp.day} &#8451</div>
                        <div class="weather-info">night:${dailyWeather.temp.night} &#8451</div>
                        <div class="weather-info">Feels like</div>
                        <div class="weather-info">day:${dailyWeather.feelsLike.day}&#8451</div>
                        <div class="weather-info">night:${dailyWeather.feelsLike.night}&#8451</div>
                        <div class="weather-info">Humidity: ${dailyWeather.humidity} %</div>
                        <div class="weather-info">Wind: ${dailyWeather.windSpeed} m/s</div>
                        <div class="weather-info">Clouds: ${dailyWeather.clouds} %</div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>

    </html>