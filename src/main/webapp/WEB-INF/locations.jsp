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
            <form method="get" action="./search">
                <button class="header-button">Back to search</button>
            </form>
        </div>
    </div>
    <div id="container">
        <div class="space-between-div"></div>
        <div class="space-between-div"></div>
        <div id="bigCenteredBox">
            <div id="inputBox">
                <c:forEach var="s" items="${locations}">
                    <form method="post" action="./weather">
                        <div class="weather-info">
                            <button type="submit">${s.country} ${s.state} ${s.name}</button>
                            <input type="hidden" name="lat" value="${s.lat}">
                            <input type="hidden" name="lon" value="${s.lon}">
                            <input type="hidden" name="country" value="${s.country}">
                            <input type="hidden" name="state" value="${s.state}">
                            <input type="hidden" name="name" value="${s.name}">
                        </div>
                    </form>
                </c:forEach>
            </div>
        </div>
</body>

</html>