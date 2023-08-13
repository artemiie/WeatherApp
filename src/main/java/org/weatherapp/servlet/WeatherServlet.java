package org.weatherapp.servlet;

import org.weatherapp.model.weather.Weather;
import org.weatherapp.service.WeatherService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/weather"})
public class WeatherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/app/search");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final String lat = req.getParameter("lat");
        final String lon = req.getParameter("lon");
        final String country = req.getParameter("country");
        final String state = req.getParameter("state");
        final String name = req.getParameter("name");

        WeatherService weatherService = new WeatherService();
        Weather weatherForecast = weatherService.getWeatherForecast(lat, lon);

        req.setAttribute("weather", weatherForecast);
        req.setAttribute("country", country);
        req.setAttribute("state", state);
        req.setAttribute("name", name);
        req.getRequestDispatcher("/WEB-INF/weather.jsp").forward(req, resp);
    }

}