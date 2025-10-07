package se233.chapter2.model;

import se233.chapter2.view.TopPane;

public class CurrencyEntity {
    private Double rate;
    private String date;
    public static String baseCurrency;
    public CurrencyEntity(Double rate, String date) {
        this.rate = rate;
        this.date = date;
    }

    public Double getRate() {
        return rate;
    }

    public String getTimeStamp() {
        return date;
    }

    public static void setBaseCurrency(String baseCurrency) {
        CurrencyEntity.baseCurrency = baseCurrency;
    }

    public static String getBaseCurrency() {
        return baseCurrency;
    }

    @Override
    public String toString() {
        return String.format("%s %.4f", date, rate);
    }
}
