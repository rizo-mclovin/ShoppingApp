package com.connor.spreadsearch.api;

import com.connor.spreadsearch.api.parsers.Binance;
import com.connor.spreadsearch.api.parsers.LBank;

import java.io.IOException;

public class Parser {
    public static void main(String[] args) throws IOException {
//        Binance binance = new Binance();
//        binance.parser();

        LBank lBank = new LBank();
        lBank.parser();
    }
}