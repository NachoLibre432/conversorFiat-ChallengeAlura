package com.exchange.currencies;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JComboBox;

public class CurrencyFactory {
    private static CurrencyFactory instance = null;
    private Map<String, Currency> currencies;

    private CurrencyFactory() {
        currencies = new HashMap<>();
        loadCurrencies();
    }

    public static CurrencyFactory getInstance() {
        if (instance == null) {
            instance = new CurrencyFactory();
        }
        return instance;
    }

    public Currency getCurrency(String currencyCode) {
        Currency currency = currencies.get(currencyCode);
        if (currency == null) {
            throw new IllegalArgumentException("Unsupported currency code: " + currencyCode);
        }
        return currency;
    }

    public void loadCurrencies() {
        try (InputStream input = CurrencyFactory.class.getClassLoader()
                .getResourceAsStream("com/exchange/currencies/currencies.properties/currencies.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            for (String currencyCode : properties.stringPropertyNames()) {
                String[] currencyValues = properties.getProperty(currencyCode).split(",");
                Currency currency = new CurrencyImpl(currencyCode, currencyValues[0].trim());
                for (int i = 1; i < currencyValues.length; i += 2) {
                    Currency exchangeCurrency = getCurrency(currencyValues[i].trim());
                    double exchangeRate = Double.parseDouble(currencyValues[i + 1].trim());
                    currency.setExchangeRate(exchangeCurrency, exchangeRate);
                }
                currencies.put(currencyCode, currency);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Currency[] getAvailableCurrencies() {
        return currencies.values().toArray(new Currency[0]);
    }
    
    public Currency getCurrencyFromComboBox(JComboBox comboBox) {
        String selectedCurrencyCode = (String) comboBox.getSelectedItem();
        Currency selectedCurrency = getCurrency(selectedCurrencyCode);
        return selectedCurrency;
    }
}
