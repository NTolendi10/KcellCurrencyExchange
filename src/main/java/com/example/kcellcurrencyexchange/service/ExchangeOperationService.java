package com.example.kcellcurrencyexchange.service;

import com.example.kcellcurrencyexchange.model.ConvertCurrencyRequestDTO;
import com.example.kcellcurrencyexchange.model.CurrencyRate;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.kcellcurrencyexchange.model.Constants.*;

@Service
@RequiredArgsConstructor
public class ExchangeOperationService {

    private final JdbcTemplate jdbcTemplate;

    public String convertCurrency (ConvertCurrencyRequestDTO request) {
        String[] toCurrencies = request.getToCurrencies();
        String fromCurrency = request.getFromCurrency();
        Double amount = request.getAmount();

        Optional.ofNullable(toCurrencies).orElseThrow(() -> new IllegalArgumentException("toCurrencies cannot be null"));
        Optional.ofNullable(fromCurrency).orElseThrow(() -> new IllegalArgumentException("fromCurrency cannot be null"));
        Optional.ofNullable(amount).orElseThrow(() -> new IllegalArgumentException("amount cannot be null"));
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

            recordExchangeOperation(fromCurrency, toCurrency, amount, convertedAmount);
        }

        return result.toString();
    }


    //Не смог подключить хибернейт, так бы переписал в @Repository
    public List<CurrencyRate> getCurrentExchangeRates() {
        return jdbcTemplate.query(GET_ALL_CURRENCY_RATES,
                (rs, rowNum) -> new CurrencyRate(rs.getString("currency"), rs.getDouble("rate")));
    }

    public List<Map<String, Object>> getExchangeHistory() {
        return jdbcTemplate.queryForList(GET_EXCHANGE_HISTORY);
    }

    public void recordExchangeOperation(String fromCurrency, String toCurrency, double amount, double result) {
        jdbcTemplate.update(
                INSERT_INTO_EXCHANE_OPERATION,
                fromCurrency, toCurrency, amount, result, new Date());
    }
}
