package org.weatherapp.model.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    @JsonProperty("current")
    private CurrentWeather current;
    @JsonProperty("daily")
    private List<DailyWeather> dailyList;

    public CurrentWeather getCurrent() {
        return current;
    }

    public void setCurrent(CurrentWeather current) {
        this.current = current;
    }

    public List<DailyWeather> getDailyList() {
        return dailyList;
    }

    public void setDailyList(List<DailyWeather> dailyList) {
        this.dailyList = dailyList;
    }
}
