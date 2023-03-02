package com.connor.spreadsearch;

public class Test {
    public static void main(String[] args) {
        String str = "DOGE_BTC";
        String c = "USDT";

        boolean isExist = str.indexOf(c) != -1;

        System.out.println(isExist);
    }
}
