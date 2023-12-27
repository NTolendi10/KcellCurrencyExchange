package com.example.kcellcurrencyexchange.service;

import com.example.kcellcurrencyexchange.model.ExchangeOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExchangeOperationService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExchangeOperationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void recordExchangeOperation(String fromCurrency, String toCurrency, double amount, double result) {
        jdbcTemplate.update(
                "INSERT INTO exchange_operation (from_currency, to_currency, amount, result, operation_time) VALUES (?, ?, ?, ?, ?)",
                fromCurrency, toCurrency, amount, result, new Date());
    }
}
