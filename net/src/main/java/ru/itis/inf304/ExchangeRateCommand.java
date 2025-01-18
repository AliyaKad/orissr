package ru.itis.inf304;

import ru.itis.inf304.Client.CurrencyClient;

public class ExchangeRateCommand {
    private CurrencyClient currencyClient;

    public ExchangeRateCommand(String apiKey) {
        this.currencyClient = new CurrencyClient(apiKey);
    }

    public String execute(String command) {
        String[] parts = command.split(" ");
        if (parts.length < 3) {
            return "Please specify the base currency and target currency (e.g., exchange USD EUR).";
        }
        String baseCurrency = parts[1].toUpperCase();
        String targetCurrency = parts[2].toUpperCase();

        double rate = currencyClient.getRate(baseCurrency, targetCurrency);
        if (rate != -1) {
            return "Exchange rate from " + baseCurrency + " to " + targetCurrency + ": " + rate;
        } else {
            return "Currency not found.";
        }
    }
}

