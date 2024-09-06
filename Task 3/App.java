package com.example;

import yahoofinance.YahooFinance;
import yahoofinance.Stock;
import java.math.BigDecimal;

public class App {
    public static void main(String[] args) {
        try {
            Stock stock = YahooFinance.get("DIA"); // Example ticker symbol
            if (stock != null) {
                BigDecimal price = stock.getQuote().getPrice();
                BigDecimal change = stock.getQuote().getChangeInPercent();
                BigDecimal peg = stock.getStats().getPeg();
                BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();
                
                System.out.println("Price: " + price);
                System.out.println("Change: " + change);
                System.out.println("PEG: " + peg);
                System.out.println("Dividend: " + dividend);
                
                stock.print(); // Print stock details if the method is available
            } else {
                System.out.println("Stock data is null.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while fetching stock data: " + e.getMessage());
        }
    }
}
