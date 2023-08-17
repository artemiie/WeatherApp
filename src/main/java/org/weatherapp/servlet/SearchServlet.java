package org.weatherapp.servlet;

import org.weatherapp.model.Location;
import org.weatherapp.service.WeatherService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.weatherapp.util.Utils.isValid;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/search.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final String cityName = req.getParameter("cityInput");

        if (!isValid(cityName)) {
            req.setAttribute("errorCityName", "City name is not valid.");
            req.getRequestDispatcher("/WEB-INF/search.jsp").forward(req, resp);
            return;
        }

        WeatherService weatherService = new WeatherService();
        List<Location> locations = weatherService.getLocations(cityName);

        if (locations.isEmpty()) {
            req.setAttribute("errorCityName", "City is not found.");
            req.getRequestDispatcher("/WEB-INF/search.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("locations", locations);
        req.getRequestDispatcher("/WEB-INF/locations.jsp").forward(req, resp);
    }
}