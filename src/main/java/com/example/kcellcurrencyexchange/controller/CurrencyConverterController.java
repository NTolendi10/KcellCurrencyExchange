package com.example.kcellcurrencyexchange.controller;

import com.example.kcellcurrencyexchange.model.CurrencyRate;
import com.example.kcellcurrencyexchange.service.ExchangeOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CurrencyConverterController {

    private final JdbcTemplate jdbcTemplate;
    private final ExchangeOperationService exchangeOperationService;

    @Autowired
    public CurrencyConverterController(JdbcTemplate jdbcTemplate, ExchangeOperationService exchangeOperationService) {
        this.jdbcTemplate = jdbcTemplate;
        this.exchangeOperationService = exchangeOperationService;
    }

    @GetMapping("/convert")
    public String convertCurrency(@RequestParam double amount, @RequestParam String fromCurrency, @RequestParam String[] toCurrencies) {
        List<CurrencyRate> rates = jdbcTemplate.query("SELECT * FROM currency_rate",
                (rs, rowNum) -> new CurrencyRate(rs.getString("currency"), rs.getDouble("rate")));

        Map<String, Double> ratesMap = rates.stream().collect(Collectors.toMap(CurrencyRate::getCurrency, CurrencyRate::getRate));
        StringBuilder result = new StringBuilder();

        for (String toCurrency : toCurrencies) {
            Double fromRate = ratesMap.getOrDefault(fromCurrency.toUpperCase(), null);
            Double toRate = ratesMap.getOrDefault(toCurrency.toUpperCase(), null);

            if (fromRate == null || toRate == null) {
                result.append(String.format("Ошибка: курс для валюты %s или %s не найден.\n", fromCurrency, toCurrency));
                continue;
            }

            double convertedAmount = amount * fromRate / toRate;
            result.append(String.format("%.2f %s в %s = %.2f\n", amount, fromCurrency, toCurrency, convertedAmount));

            // Запись операции в базу данных
            exchangeOperationService.recordExchangeOperation(fromCurrency, toCurrency, amount, convertedAmount);
        }

        return result.toString();
    }

    @GetMapping("/rates")
    public List<CurrencyRate> getCurrentExchangeRates() {
        return jdbcTemplate.query("SELECT * FROM currency_rate",
                (rs, rowNum) -> new CurrencyRate(rs.getString("currency"), rs.getDouble("rate")));
    }

    @GetMapping("/exchange-history")
    public List<Map<String, Object>> getExchangeHistory() {
        return jdbcTemplate.queryForList("SELECT * FROM exchange_operation");
    }
}
