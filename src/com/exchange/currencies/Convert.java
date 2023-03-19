package com.exchange.currencies;

import com.exchange.currencies.exceptions.CurrencyConversionException;

public class Convert {
  public static double convert(Currency from, Currency to, double amount) throws CurrencyConversionException {
    if (from == null || to == null) {
      throw new IllegalArgumentException("Both currencies must be provided.");
    }

    if (amount < 0) {
      throw new ArithmeticException("Amount must be a positive number.");
    }

    if (from.equals(to)) {
      return amount;
    }

    double exchangeRate = from.getExchangeRate(to);
    if (Double.isNaN(exchangeRate)) {
      throw new CurrencyConversionException("Cannot convert from " + from.getCode() + " to " + to.getCode());
    }

    return amount * exchangeRate;
  }
}
