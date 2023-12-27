package com.example.kcellcurrencyexchange.service;

import com.example.kcellcurrencyexchange.model.ConvertCurrencyRequestDTO;
import com.example.kcellcurrencyexchange.model.ConvertCurrencyResponseDTO;
import com.example.kcellcurrencyexchange.model.CurrencyRate;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.kcellcurrencyexchange.model.Constants.*;

@Service
@RequiredArgsConstructor
public class ExchangeOperationService {

    private final JdbcTemplate jdbcTemplate;
    private final String exceptionMessage = "Ошибка: курс для валюты %s или %s не найден.\n";

    public ConvertCurrencyResponseDTO convertCurrency(ConvertCurrencyRequestDTO request) throws IllegalArgumentException {
        validateRequest(request);
        Map<String, BigDecimal> ratesMap = fetchCurrencyRates();
        ConvertCurrencyResponseDTO response = createResponseObject(request);

        Map<String, BigDecimal> convertedAmounts = new HashMap<>();

        StringBuilder message = new StringBuilder();
        for (String toCurrency : request.getToCurrencies()) {
            BigDecimal fromRate = ratesMap.get(request.getFromCurrency().toUpperCase());
            BigDecimal toRate = ratesMap.get(toCurrency.toUpperCase());

            if (fromRate == null || toRate == null) {
                message.append(String.format(exceptionMessage, request.getFromCurrency(), toCurrency));
                continue;
            }

            BigDecimal convertedAmount = convertAmount(
                    request.getAmount(),
                    fromRate,
                    toRate
            );
            convertedAmounts.put(toCurrency, convertedAmount);

            recordExchangeOperation(request.getFromCurrency(), toCurrency, request.getAmount(), convertedAmount.doubleValue());
        }

        response.setConvertedAmounts(convertedAmounts);
        response.setMessage(message.toString());
        return response;
    }

    private BigDecimal convertAmount(BigDecimal amount, BigDecimal fromRate, BigDecimal toRate) {
        return amount.multiply(fromRate).divide(toRate, 2, RoundingMode.HALF_UP);
    }

    private ConvertCurrencyResponseDTO createResponseObject(ConvertCurrencyRequestDTO request) {
        ConvertCurrencyResponseDTO response = new ConvertCurrencyResponseDTO();
        response.setFromCurrency(request.getFromCurrency());
        response.setOriginalAmount(request.getAmount());

        return response;
    }

    private void validateRequest(ConvertCurrencyRequestDTO request) {
        if (request.getToCurrencies() == null) throw new IllegalArgumentException("toCurrencies cannot be null");
        if (request.getFromCurrency() == null) throw new IllegalArgumentException("fromCurrency cannot be null");
        if (request.getAmount() == null) throw new IllegalArgumentException("amount cannot be null");
    }

    private Map<String, BigDecimal> fetchCurrencyRates() {
        List<CurrencyRate> rates = getCurrentExchangeRates();
        return rates.stream().collect(Collectors.toMap(CurrencyRate::getCurrency, CurrencyRate::getRate));
    }

    private double convertAmount(Double amount, Double fromRate, Double toRate) {
        return amount * fromRate / toRate;
    }

    private String formatResult(double amount, String fromCurrency, String toCurrency, double convertedAmount) {
        return String.format("%.2f %s в %s = %.2f\n", amount, fromCurrency, toCurrency, convertedAmount);
    }


    //Не смог подключить хибернейт, так бы переписал в @Repository
    public List<CurrencyRate> getCurrentExchangeRates() {
        return jdbcTemplate.query(GET_ALL_CURRENCY_RATES,
                (rs, rowNum) -> new CurrencyRate(rs.getString("currency"), BigDecimal.valueOf(rs.getDouble("rate"))));
    }

    public List<Map<String, Object>> getExchangeHistory() {
        return jdbcTemplate.queryForList(GET_EXCHANGE_HISTORY);
    }

    public void recordExchangeOperation(String fromCurrency, String toCurrency, BigDecimal amount, double result) {
        jdbcTemplate.update(
                INSERT_INTO_EXCHANE_OPERATION,
                fromCurrency, toCurrency, amount, result, new Date());
    }
}
