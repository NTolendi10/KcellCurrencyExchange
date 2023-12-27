package com.example.kcellcurrencyexchange.model;

import lombok.Data;

@Data
public class ConvertCurrencyRequestDTO {

    private Double amount;
    private String fromCurrency;
    private String[] toCurrencies;
}
