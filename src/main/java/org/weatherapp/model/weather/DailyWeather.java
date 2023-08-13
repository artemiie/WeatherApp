package org.weatherapp.model.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.weatherapp.model.UnixTimestampDeserializer;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyWeather {
    @JsonProperty("dt")
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private LocalDateTime dateTime;
    @JsonProperty("sunrise")
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private LocalDateTime sunriseDt;
    @JsonProperty("sunset")
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private LocalDateTime sunsetDt;
    @JsonProperty("temp")
    private Temperature temp;
    @JsonProperty("feels_like")
    private FeelsLike feelsLike;
    @JsonProperty("humidity")
    private long humidity;
    @JsonProperty("clouds")
    private long clouds;
    @JsonProperty("wind_speed")
    private double windSpeed;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getSunriseDt() {
        return sunriseDt;
    }

    public void setSunriseDt(LocalDateTime sunriseDt) {
        this.sunriseDt = sunriseDt;
    }

    public LocalDateTime getSunsetDt() {
        return sunsetDt;
    }

    public void setSunsetDt(LocalDateTime sunsetDt) {
        this.sunsetDt = sunsetDt;
    }

    public Temperature getTemp() {
        return temp;
    }

    public void setTemp(Temperature temp) {
        this.temp = temp;
    }

    public FeelsLike getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(FeelsLike feelsLike) {
        this.feelsLike = feelsLike;
    }

    public long getHumidity() {
        return humidity;
    }

    public void setHumidity(long humidity) {
        this.humidity = humidity;
    }

    public long getClouds() {
        return clouds;
    }

    public void setClouds(long clouds) {
        this.clouds = clouds;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
}
