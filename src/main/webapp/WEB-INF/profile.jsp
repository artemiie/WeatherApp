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
                <div class="inputBox" id="scrollable-div">
                    <c:choose>
                        <c:when test="${isUserLogged}">
                            <div class="weather-info"> <label class="input-label">Hi, ${user.name} !</label></div>
                            <c:forEach var="s" items="${userLocations}">
                                <form method="post" action="./weather">
                                    <div class="weather-info">
                                        <button type="submit">${s.country} ${s.state} ${s.city}</button>
                                        <input type="hidden" name="lat" value="${s.lat}">
                                        <input type="hidden" name="lon" value="${s.lon}">
                                        <input type="hidden" name="country" value="${s.country}">
                                        <input type="hidden" name="state" value="${s.state}">
                                        <input type="hidden" name="city" value="${s.city}">
                                        <input type="hidden" name="isSubscriptionActive" value="true">
                                    </div>
                                </form>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="weather-info"><label class="input-label">User is not logged in.</label></div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </body>

    </html>