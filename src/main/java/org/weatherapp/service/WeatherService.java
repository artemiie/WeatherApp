package org.weatherapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.weatherapp.model.Location;
import org.weatherapp.model.weather.Weather;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class WeatherService {

    private static final String BASE_API_URL = "https://api.openweathermap.org/";
    private static final String WEATHER_API_URL = "data/3.0/onecall?";
    private static final String GEOCODING_API_URL = "geo/1.0/direct?";
    private static final String API_KEY_SUFIX = "&appid=";
    private static final String API_KEY = "";
    private static final String CITY_NAME_SUFIX = "&q=";
    private static final String LATITUDE_SUFIX = "&lat=";
    private static final String LONGITUDE_SUFIX = "&lon=";
    //Somehow without explicit limit api returns only 1 object
    private static final String LIMIT = "&limit=10";
    //For temperature in Celsius and wind speed in meter/sec
    private static final String UNITS = "&units=metric";
    private static final String EXCLUDE = "&exclude=minutely,hourly,alerts";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Weather getWeatherForecast(String lat, String lon) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(buildUriForWeatherForecastRequest(lat, lon))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        Weather weatherResponse = new Weather();
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            weatherResponse = objectMapper.readValue(response.body(), Weather.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return weatherResponse;
    }

    public List<Location> getLocations(String cityName) {
        cityName = cityName.replaceAll(" ", "_");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(buildUriForGeocodingRequest(cityName))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        Location[] locations = new Location[0];
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            locations = new Gson().fromJson(response.body(), Location[].class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return Arrays.asList(locations);
    }

    private static URI buildUriForWeatherForecastRequest(String lat, String lon) {
        return URI.create(BASE_API_URL + WEATHER_API_URL + LATITUDE_SUFIX + lat + LONGITUDE_SUFIX + lon + API_KEY_SUFIX + API_KEY + UNITS + EXCLUDE);
    }

    private static URI buildUriForGeocodingRequest(String nameOfLocation) {
        return URI.create(BASE_API_URL + GEOCODING_API_URL + CITY_NAME_SUFIX + nameOfLocation + LIMIT + API_KEY_SUFIX + API_KEY);
    }
}
