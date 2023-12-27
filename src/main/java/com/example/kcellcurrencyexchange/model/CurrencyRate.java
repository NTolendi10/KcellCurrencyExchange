package com.example.kcellcurrencyexchange.model;

public class CurrencyRate {

    private String currency; // Название валюты, например, "USD", "EUR"
    private double rate;     // Курс валюты относительно KZT

    // Конструктор по умолчанию
    public CurrencyRate() {
    }

    // Конструктор с параметрами
    public CurrencyRate(String currency, double rate) {
        this.currency = currency;
        this.rate = rate;
    }

    // Геттер для currency
    public String getCurrency() {
        return currency;
    }

    // Сеттер для currency
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    // Геттер для rate
    public double getRate() {
        return rate;
    }

    // Сеттер для rate
    public void setRate(double rate) {
        this.rate = rate;
    }
}
