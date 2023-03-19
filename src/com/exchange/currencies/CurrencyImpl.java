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

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setExchangeRate(Currency toCurrency, double rate) {
        exchangeRates.put(toCurrency, rate);
    }

    public double getExchangeRate(Currency toCurrency) {
        if (this == toCurrency) {
            return 1.0;
        }
        return exchangeRates.getOrDefault(toCurrency, 0.0);
    }


	public String getConversionUnits() {
		StringBuilder sb = new StringBuilder();
		sb.append("1 ").append(getCode()).append(" =\n");
		for (Map.Entry<Currency, Double> entry : exchangeRates.entrySet()) {
			Currency toCurrency = entry.getKey();
			double rate = entry.getValue();
			sb.append(String.format("%.4f", rate)).append(" ").append(toCurrency.getCode()).append("\n");
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
	    return code + " - " + name;
	}

}
