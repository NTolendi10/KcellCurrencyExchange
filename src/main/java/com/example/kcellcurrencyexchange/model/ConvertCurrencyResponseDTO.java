package com.example.kcellcurrencyexchange.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ConvertCurrencyResponseDTO {
    private String fromCurrency;
    private BigDecimal originalAmount;
    private Map<String, BigDecimal> convertedAmounts;
    private String message;

}
