package com.example.kcellcurrencyexchange.model;

import java.util.Date;

public class ExchangeOperation {
    private Long id;             // Уникальный идентификатор операции
    private String fromCurrency; // Исходная валюта
    private String toCurrency;   // Целевая валюта
    private double amount;       // Сумма в исходной валюте
    private double result;       // Результат в целевой валюте
    private Date operationTime;  // Время выполнения операции

    public ExchangeOperation() {
        // Пустой конструктор
    }

    // Конструктор с параметрами
    public ExchangeOperation(Long id, String fromCurrency, String toCurrency, double amount, double result, Date operationTime) {
        this.id = id;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.result = result;
        this.operationTime = operationTime;
    }

    // Геттеры и сеттеры для всех полей
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }
}
