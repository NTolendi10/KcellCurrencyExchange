package com.example.kcellcurrencyexchange.controller;

import com.example.kcellcurrencyexchange.model.ConvertCurrencyRequestDTO;
import com.example.kcellcurrencyexchange.model.CurrencyRate;
import com.example.kcellcurrencyexchange.service.ExchangeOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CurrencyConverterController {

    private final JdbcTemplate jdbcTemplate;
    private final ExchangeOperationService exchangeOperationService;

    @GetMapping("/convert")
    public String convertCurrency(@RequestBody ConvertCurrencyRequestDTO request) {
        return exchangeOperationService.convertCurrency(request);
    }

    @GetMapping("/rates")
    public List<CurrencyRate> getCurrentExchangeRates() {
        return exchangeOperationService.getCurrentExchangeRates();
    }

    @GetMapping("/exchange-history")
    public List<Map<String, Object>> getExchangeHistory() {
        return exchangeOperationService.getExchangeHistory();
    }
}
