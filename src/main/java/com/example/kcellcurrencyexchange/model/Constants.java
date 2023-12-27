package com.example.kcellcurrencyexchange.model;

public class Constants {
    public static String GET_ALL_CURRENCY_RATES = "SELECT * FROM currency_rate";
    public static String GET_EXCHANGE_HISTORY = "SELECT * FROM exchange_operation";
    public static String INSERT_INTO_EXCHANE_OPERATION = "INSERT INTO exchange_operation " +
            "(from_currency, to_currency, amount, result, operation_time) VALUES (?, ?, ?, ?, ?)";
}
