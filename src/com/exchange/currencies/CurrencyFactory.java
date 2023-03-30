package com.exchange.currencies;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class CurrencyFactory {

    private static CurrencyFactory instance;

    private final Map<String, Currency> currencies;

    private CurrencyFactory() {
        this.currencies = new HashMap<>();
        loadCurrencies();
    }

    public static CurrencyFactory getInstance() {
        if (instance == null) {
            instance = new CurrencyFactory();
        }
        return instance;
    }

    public Currency getCurrency(String code) {
        Currency currency = currencies.get(code);
        if (currency == null) {
            throw new IllegalArgumentException("Currency " + code + " not found");
        }
        return currency;
    }

    public Set<String> getAvailableCurrencies() {
        return currencies.keySet();
    }

    public void loadCurrencies() {
        if (!currencies.isEmpty()) {
            // currencies have already been loaded
            return;
        }
        Properties properties = new Properties();
        InputStream inputStream = CurrencyFactory.class.getClassLoader().getResourceAsStream("com/exchange/currencies/currencyList/currencies.properties");
        try {
            properties.load(inputStream);
            String[] codes = properties.getProperty("codes").split(",");
            for (String code : codes) {
                String name = properties.getProperty(code);
                if (name == null) {
                    throw new RuntimeException("Error loading currency with code " + code);
                }
                Currency currency = createCurrency(code, name);
                currencies.put(code, currency);
            }
            currencies.put("MXN", createCurrency("MXN", "Mexican Peso"));

            setExchangeRates(properties);
        } catch (IOException e) {
            throw new RuntimeException("Error loading currencies from file", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    public Currency createCurrency(String code, String name) {
        return new CurrencyImpl(code, name);
    }

    private void setExchangeRates(Properties properties) {
        for (String code : currencies.keySet()) {
            Currency currency = currencies.get(code);
            for (String toCode : currencies.keySet()) {
                if (!code.equals(toCode)) {
                    Currency toCurrency = currencies.get(toCode);
                    String rateProperty = code + "." + toCode + ".rate";
                    String rateString = properties.getProperty(rateProperty);
                    if (rateString != null) {
                        double rate = Double.parseDouble(rateString);
                        currency.setExchangeRate(toCurrency, rate);
                    }
                }
            }
        }
        
        Currency usd = getCurrency("USD");
        Currency eur = getCurrency("EUR");
        Currency jpy = getCurrency("JPY");
        Currency gbp = getCurrency("GBP");
        Currency kpw = getCurrency("KPW");
        Currency mxn = getCurrency("MXN");
        
        usd.setExchangeRate(eur, 0.824646);
        usd.setExchangeRate(jpy, 104.448473);
        usd.setExchangeRate(gbp, 0.728006);
        usd.setExchangeRate(kpw, 900.000000);
        usd.setExchangeRate(mxn, 19.9518); 

        eur.setExchangeRate(usd, 1.212680);
        eur.setExchangeRate(jpy, 127.873910);
        eur.setExchangeRate(gbp, 0.889301);
        eur.setExchangeRate(kpw, 1356.930693);
        eur.setExchangeRate(mxn, 23.0637);

        jpy.setExchangeRate(usd, 0.009577);
        jpy.setExchangeRate(eur, 0.007817);
        jpy.setExchangeRate(gbp, 0.006978);
        jpy.setExchangeRate(kpw, 10.677054);
        jpy.setExchangeRate(mxn, 0.1819);

        gbp.setExchangeRate(usd, 1.373914);
        gbp.setExchangeRate(eur, 1.124782);
        gbp.setExchangeRate(jpy, 143.167277);
        gbp.setExchangeRate(kpw, 1695.652174);
        gbp.setExchangeRate(mxn, 25.2928);

        kpw.setExchangeRate(usd, 0.001111);
        mxn.setExchangeRate(usd, 0.0501);

        
    }
}
