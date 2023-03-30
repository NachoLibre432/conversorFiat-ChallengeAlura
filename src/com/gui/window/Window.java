package com.gui.window;

import javax.swing.*;

import com.exchange.currencies.Convert;
import com.exchange.currencies.Currency;
import com.exchange.currencies.CurrencyFactory;
import com.exchange.currencies.exceptions.CurrencyConversionException;
import com.exchange.currencies.CurrencyImpl;

public class Window {

    public static String menu() {
        String message = "Seleccione una opción de conversión";
        String[] options = { "Conversor de monedas", "Conversor de temperaturas", "Salir" };

        int option = JOptionPane.showOptionDialog(null, message, "Conversor", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        switch (option) {
            case 0:
                return "CURRENCY";
            case 1:
                return "TEMPERATURE";
            case 2:
                return "EXIT";
            default:
                // No es necesario crear una instancia de CurrencyFactory para esta opción
                return "EXIT";
        }
    }

    public static void nextWindow(String window) {
        switch (window) {
            case "CURRENCY":

                // Se crea una instancia de CurrencyFactory y usamos su método
                // getAvailableCurrencies

                CurrencyFactory factory = CurrencyFactory.getInstance();
                String[] currencies = factory.getAvailableCurrencies().toArray(new String[0]);

                JComboBox<String> fromCombo = new JComboBox<>(currencies);
                JComboBox<String> toCombo = new JComboBox<>(currencies);

                JPanel panel = new JPanel();
                panel.add(new JLabel("Moneda de origen: "));
                panel.add(fromCombo);
                panel.add(new JLabel("Moneda de destino: "));
                panel.add(toCombo);

                double amount = 0;
                boolean validAmount = false;
                while (!validAmount) {
                    String amountString = JOptionPane.showInputDialog(null, "Introduce la cantidad a convertir:");
                    try {
                        amount = Double.parseDouble(amountString);
                        validAmount = true;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "La cantidad no es válida.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                int resultDialog = JOptionPane.showConfirmDialog(null, panel, "Conversor de monedas",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (resultDialog == JOptionPane.OK_OPTION) {

                    String fromCode = (String) fromCombo.getSelectedItem();
                    String toCode = (String) toCombo.getSelectedItem();
                    System.out.println("Selected currency: " + toCode);

                    Currency fromCurrency = new CurrencyImpl(fromCode);
                    Currency toCurrency = new CurrencyImpl(toCode);

                    try {
                        double conversionResult = Convert.convert(fromCurrency, toCurrency, amount);
                        JOptionPane.showMessageDialog(null, amount + " " + fromCode + " = " + conversionResult + " " + toCode);
                    } catch (CurrencyConversionException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error de conversión", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Se produjo un error al crear las monedas.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            
            case "TEMPERATURE":
                double temperature = 0;
                boolean validTemperature = false;
                while (!validTemperature) {
                    String temperatureString = JOptionPane.showInputDialog(null, "Introduce la temperatura a convertir:");
                    try {
                        temperature = Double.parseDouble(temperatureString);
                        validTemperature = true;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "La temperatura no es válida.", "Error",
                        		JOptionPane.ERROR_MESSAGE);
				}
			}

			String[] options = { "Celsius a Fahrenheit", "Fahrenheit a Celsius" };
			int option = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Conversor de temperaturas",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

			double temperatureResult = 0;

			if (option == 0) {
				temperatureResult = temperature * 9 / 5 + 32;
				JOptionPane.showMessageDialog(null,
						temperature + " grados Celsius = " + temperatureResult + " grados Fahrenheit");

			} else {
				temperatureResult = (temperature - 32) * 5 / 9;
				JOptionPane.showMessageDialog(null,
						temperature + " grados Fahrenheit = " + temperatureResult + " grados Celsius");
			}
			break;
		}
	}
}