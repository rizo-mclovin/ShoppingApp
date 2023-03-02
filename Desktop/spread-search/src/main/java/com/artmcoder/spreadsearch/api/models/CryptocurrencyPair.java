package com.artmcoder.spreadsearch.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CryptocurrencyPair {
    private String exchange;
    private String firstCrypto;
    private String secondCrypto;
    private Double amount;
}
