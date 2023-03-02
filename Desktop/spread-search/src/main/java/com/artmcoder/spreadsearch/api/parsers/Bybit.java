package com.artmcoder.spreadsearch.api.parsers;

import com.artmcoder.spreadsearch.api.models.CryptocurrencyPair;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.C;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author John
 */

@SuppressWarnings("all")
public class Bybit {
    public List<CryptocurrencyPair> parser() {
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();
        try {
            cryptocurrencyPairs.addAll(usdtParser());
        } catch (IOException e) {
            e.printStackTrace();
        }
        cryptocurrencyPairs.forEach(System.out::println);
        return cryptocurrencyPairs;
    }


    public List<CryptocurrencyPair> usdtParser() throws IOException{
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        String req = "https://api-testnet.bybit.com/v2/public/tickers";
        String response = restTemplate.getForObject(req, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode obj = objectMapperForMexc.readTree(response);

        for(int i = 0; i < 195; i++) {
            CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
            cryptocurrencyPair.setExchange("ByBit");

            String firstCrypto = obj.get("result").get(i).get("symbol").textValue();
            boolean isSecondCryptoUSDT = firstCrypto.indexOf("USDT") !=-1;
            if (isSecondCryptoUSDT == true){
                cryptocurrencyPair.setFirstCrypto(firstCrypto.replace("USDT", ""));
                cryptocurrencyPair.setAmount(Double.valueOf(obj.get("result").get(i).get("index_price").textValue()));
                cryptocurrencyPair.setSecondCrypto("USDT");

                cryptocurrencyPairs.add(cryptocurrencyPair);
            }else {
                continue;
            }
        }

        return cryptocurrencyPairs;
    }

}