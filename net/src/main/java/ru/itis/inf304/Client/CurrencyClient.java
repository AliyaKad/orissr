package ru.itis.inf304.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class CurrencyClient {
    private static final String API_URL = "https://openexchangerates.org/api/latest.json?app_id=";
    private String apiKey;

    public CurrencyClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getExchangeRate(String baseCurrency) {
        try {
            URL url = new URL(API_URL + apiKey + "&base=" + baseCurrency);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                return null;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public double getRate(String baseCurrency, String targetCurrency) {
        String response = getExchangeRate(baseCurrency);
        if (response != null) {
            JSONObject json = new JSONObject(response);
            if (json.has("rates") && json.getJSONObject("rates").has(targetCurrency)) {
                return json.getJSONObject("rates").getDouble(targetCurrency);
            }
        }
        return -1;
    }
}
