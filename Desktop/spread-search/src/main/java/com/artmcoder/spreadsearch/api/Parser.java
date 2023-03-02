package com.artmcoder.spreadsearch.api;


import com.artmcoder.spreadsearch.api.parsers.*;

import java.io.IOException;

public class Parser {
    public static void main(String[] args) throws IOException {
        System.out.println("         BINANCE           ");
        Binance binance = new Binance();
        binance.parser();

        System.out.println("         KUCOIN           ");
        KuCoin kuCoin = new KuCoin();
        kuCoin.parser();

        System.out.println("         LBANK           ");
        LBank lBank = new LBank();
        lBank.parser();

        System.out.println("         MEXC           ");
        Mexc mexc = new Mexc();
        mexc.parser();

        Huobi huobi = new Huobi();
        huobi.parser();

        Bybit bybit = new Bybit();
        bybit.parser();
    }
}