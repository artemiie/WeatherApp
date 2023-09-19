package org.weatherapp.servlet;

import org.weatherapp.exception.SearchParametersException;
import org.weatherapp.model.Location;
import org.weatherapp.service.WeatherService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.weatherapp.util.Utils.isStringNotNullNotEmptyNotBlank;

@WebServlet("/search")
public class SearchServlet extends WeatherMainServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/search.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String cityName = req.getParameter("cityInput");

        if (!isStringNotNullNotEmptyNotBlank(cityName)) {
            req.setAttribute("errorCityName", "City name is not valid.");
            throw new SearchParametersException("Search parameter CITY=[" + cityName + "] not valid.");
        }

        WeatherService weatherService = new WeatherService();
        List<Location> locations = weatherService.getLocations(cityName);

        if (locations.isEmpty()) {
            req.setAttribute("errorCityName", "City is not found.");
            throw new SearchParametersException("Nothing was found for CITY=[" + cityName + "].");
        }

        req.setAttribute("locations", locations);
        resp.sendRedirect("/app/locations");
    }
}