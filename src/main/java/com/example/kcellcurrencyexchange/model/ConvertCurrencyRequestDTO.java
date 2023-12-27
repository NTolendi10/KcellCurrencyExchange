package com.example.kcellcurrencyexchange.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConvertCurrencyRequestDTO {

    private BigDecimal amount;
    private String fromCurrency;
    private String[] toCurrencies;
}
