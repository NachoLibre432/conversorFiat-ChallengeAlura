package com;

import java.io.IOException;

import com.exchange.currencies.Currency;
import com.exchange.currencies.CurrencyFactory;
import com.gui.window.Window;

public class Main {

	public static void main(String[] args) {
	    try {
	    	CurrencyFactory currencyFactory = CurrencyFactory.getInstance();
	    	currencyFactory.loadCurrencies();
	    } catch (Exception e) {
	        System.out.println("Error al cargar las monedas: " + e.getMessage());
	    }

	    Window.nextWindow(Window.menu());

	    
        String window = "MENU";

        while (!window.equals("EXIT")) {
            switch (window) {
                case "MENU":
                    window = Window.menu();
                    break;
                case "CURRENCY":
                    Window.nextWindow("CURRENCY");
                    window = "MENU";
                    break;
                case "TEMPERATURE":
                    Window.nextWindow("TEMPERATURE");
                    window = "MENU";
                    break;
                default:
                    window = "EXIT";
                    break;
            }
        }
    }
}
