package com.artmcoder.spreadsearch.api.parsers;

import com.artmcoder.spreadsearch.api.models.CryptocurrencyPair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@SuppressWarnings("all")
public class Binance {

    public List<CryptocurrencyPair> parser() {
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();
        try {
            cryptocurrencyPairs.addAll(usdtParser());
            cryptocurrencyPairs.addAll(btcParser());
        } catch (IOException e) {
            e.printStackTrace();
        }
        cryptocurrencyPairs.forEach(System.out::println);
        return cryptocurrencyPairs;
    }


    private List<CryptocurrencyPair> usdtParser() throws IOException{
    List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Document binance = binance = Jsoup.connect("https://www.binance.com/en/markets/spot-USDT?p=" + i).get();
            Elements binanceCryptoNames = binance.getElementsByClass("css-vlibs4");
            for (Element crypto : binanceCryptoNames) {
                CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
                cryptocurrencyPair.setExchange("Binance");
                cryptocurrencyPair.setFirstCrypto(crypto.getElementsByClass("css-17wnpgm").text());
                cryptocurrencyPair.setSecondCrypto("USDT");
                cryptocurrencyPair.setAmount(getAmount(crypto.getElementsByClass("css-1r1yofv").first().child(1).text()));
                cryptocurrencyPairs.add(cryptocurrencyPair);
            }
        }
        return cryptocurrencyPairs;
    }

    public List<CryptocurrencyPair> btcParser() throws IOException{
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Document binance = binance = Jsoup.connect("https://www.binance.com/en/markets/spot-BTC?p=" + i).get();
            Elements binanceCryptoNames = binance.getElementsByClass("css-vlibs4");
            for (Element crypto : binanceCryptoNames) {
                CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
                cryptocurrencyPair.setExchange("Binance");
                cryptocurrencyPair.setFirstCrypto(crypto.getElementsByClass("css-17wnpgm").text());
                cryptocurrencyPair.setSecondCrypto("BTC");
                cryptocurrencyPair.setAmount(getAmount(crypto.getElementsByClass("css-1r1yofv").first().child(1).text()));
                cryptocurrencyPairs.add(cryptocurrencyPair);
            }
        }
        return cryptocurrencyPairs;
    }


    private Double getAmount(String amountString) {
        Double amount = Double.valueOf(amountString.replace("/ $", "").replace(",", ""));
        return amount;
    }
}