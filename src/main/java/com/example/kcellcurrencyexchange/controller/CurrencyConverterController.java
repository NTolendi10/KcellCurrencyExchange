package com.example.kcellcurrencyexchange.controller;

import com.example.kcellcurrencyexchange.model.ConvertCurrencyRequestDTO;
import com.example.kcellcurrencyexchange.model.ConvertCurrencyResponseDTO;
import com.example.kcellcurrencyexchange.model.CurrencyRate;
import com.example.kcellcurrencyexchange.service.ExchangeOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CurrencyConverterController {

    private final ExchangeOperationService exchangeOperationService;

    @GetMapping("/convert")
    public ResponseEntity<ConvertCurrencyResponseDTO> convertCurrency(@RequestBody ConvertCurrencyRequestDTO request) {
        try {
            return ResponseEntity.ok(exchangeOperationService.convertCurrency(request));
        }  catch (IllegalArgumentException e) {
            ConvertCurrencyResponseDTO errorResponse = new ConvertCurrencyResponseDTO();
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
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
