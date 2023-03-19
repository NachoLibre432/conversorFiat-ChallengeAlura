package com.exchange.currencies;

public interface Currency {
    String getCode();
    String getName();
    void setExchangeRate(Currency toCurrency, double rate);
    double getExchangeRate(Currency toCurrency);
}

