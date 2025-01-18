package ru.itis.inf304;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import ru.itis.inf304.Client.HttpClientWeather;

public class WeatherCommand {
    private HttpClientWeather httpClient;

    public WeatherCommand(String apiKey) {
        this.httpClient = new HttpClientWeather(apiKey);
    }

    public String execute(String command) {
        String[] parts = command.split(" ");
        if (parts.length < 2) {
            return "Please specify a city for the weather.";
        }
        String city = parts[1];
        Map<String, String> params = new HashMap<>();
        params.put("q", city);
        params.put("appid", httpClient.getApiKey());
        params.put("units", "metric");

        String response = httpClient.get(HttpClientWeather.getUrl(), null, params);
        if (response != null) {
            return parseWeatherResponse(response);
        } else {
            return "Error fetching weather data.";
        }
    }

    private String parseWeatherResponse(String response) {
        try {
            JSONObject json = new JSONObject(response);
            if (json.has("name") && json.has("main")) {
                String city = json.getString("name");
                JSONObject main = json.getJSONObject("main");
                double temperature = main.getDouble("temp");
                return "Weather in " + city + ": " + temperature + "°C";
            } else {
                return "Error: Invalid response format.";
            }
        } catch (Exception e) {
            return "Error parsing weather data: " + e.getMessage();
        }
    }
}
