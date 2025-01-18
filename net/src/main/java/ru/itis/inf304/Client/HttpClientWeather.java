package ru.itis.inf304.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpClientWeather {
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private String apiKey;
    public HttpClientWeather(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public static String getUrl() {
        return API_URL;
    }

    public String get(String url, Map<String, String> headers, Map<String, String> params) {
        try {
            StringBuilder urlWithParams = new StringBuilder(url);
            urlWithParams.append("?").append(getParamsString(params));

            HttpURLConnection connection = (HttpURLConnection) new URL(urlWithParams.toString()).openConnection();
            connection.setRequestMethod("GET");

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            return readResponse(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        return response.toString();
    }

    private String getParamsString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!result.isEmpty()) {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        return result.toString();
    }
}