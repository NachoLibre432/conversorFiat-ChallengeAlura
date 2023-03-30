package com.exchange.currencies;

import java.util.HashMap;
import java.util.Map;

public class CurrencyImpl implements Currency {

    private final String code;
    private final String name;
    private final Map<Currency, Double> exchangeRates;

    public CurrencyImpl(String code, String name) {
        this.code = code;
        this.name = name;
        this.exchangeRates = new HashMap<>();
    }
    
    public CurrencyImpl(String code) {
    	this(code, "");
    	}

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setExchangeRate(Currency toCurrency, double rate) {
        if (toCurrency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }

        if (this == toCurrency) {
            throw new IllegalArgumentException("Cannot set exchange rate for the same currency");
        }

        if (Double.isNaN(rate) || rate <= 0) {
            throw new IllegalArgumentException("Exchange rate must be a positive number");
        }

        exchangeRates.put(toCurrency, rate);
    }

    @Override
    public double getExchangeRate(Currency toCurrency) {
        if (toCurrency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }

        if (this == toCurrency) {
            return 1.0;
        }

        Double rate = exchangeRates.get(toCurrency);
        if (rate == null) {
            throw new IllegalArgumentException("Exchange rate not found for currency " + toCurrency.getCode());
        }

        return rate;
    }
}
