package com.connor.spreadsearch.api.parsers;

import com.connor.spreadsearch.api.models.CryptocurrencyPair;
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
    public static void main(String[] args) throws JsonProcessingException {
        getInfo();
    }



    /**
     * @return Имя связки и цену
     * @throws JsonProcessingException
     */
    public static void getInfo() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String req = "https://api-testnet.bybit.com/v2/public/tickers";
        String response = restTemplate.getForObject(req, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode obj = objectMapperForMexc.readTree(response);

        for(int i = 0; i < 195; i++) {
            System.out.println(obj.get("result").get(i).get("symbol") + " - " + obj.get("result").get(i).get("index_price"));
        }
    }

    public List<CryptocurrencyPair> usdtParser() throws IOException {
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();
        //TODO only USDT
        return Collections.emptyList();
    }
}