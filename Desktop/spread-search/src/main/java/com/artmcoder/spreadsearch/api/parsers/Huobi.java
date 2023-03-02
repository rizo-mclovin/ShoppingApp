package com.artmcoder.spreadsearch.api.parsers;

import com.artmcoder.spreadsearch.api.models.CryptocurrencyPair;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author John
 */

@SuppressWarnings("all")
public class Huobi {
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

    public List<CryptocurrencyPair> usdtParser() throws JsonProcessingException {
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();

        ObjectMapper huobiMapper = new ObjectMapper();
        JsonNode parseHuobi= huobiMapper.readTree(getAllCurrencyPairs("https://api.huobi.pro/market/tickers"));

        for(int i = 0; i < 10; i++) { // 955
            CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
            try {
                String firstCryptoName = getOneSymbolFromHuobi(parseHuobi.get("data").get(i).get("symbol").textValue()).toUpperCase();
                cryptocurrencyPair.setExchange("Huobi");

                boolean isSecondCryptoUSDT = firstCryptoName.indexOf("USDT") != -1;
                if (isSecondCryptoUSDT == true){
                    cryptocurrencyPair.setFirstCrypto(firstCryptoName.replace("USDT", ""));
                    cryptocurrencyPair.setSecondCrypto("USDT");
                    cryptocurrencyPair.setAmount(Double.valueOf(getAmount(parseHuobi.get("data").get(i).get("symbol").textValue())).doubleValue());
                    cryptocurrencyPairs.add(cryptocurrencyPair);
                }

            }catch (NullPointerException e){
                continue;
            }
        }

        return cryptocurrencyPairs;
    }


    public List<CryptocurrencyPair> btcParser() throws JsonProcessingException {
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();

        ObjectMapper huobiMapper = new ObjectMapper();
        JsonNode parseHuobi= huobiMapper.readTree(getAllCurrencyPairs("https://api.huobi.pro/market/tickers"));

        for(int i = 0; i < 10; i++) { // 955
            CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
            try {
                String firstCryptoName = getOneSymbolFromHuobi(parseHuobi.get("data").get(i).get("symbol").textValue()).toUpperCase();
                cryptocurrencyPair.setExchange("Huobi");


                boolean isSecondCryptoBTC = firstCryptoName.indexOf("BTC") != -1;
                if (isSecondCryptoBTC == true){
                    cryptocurrencyPair.setFirstCrypto(firstCryptoName.replace("BTC", ""));
                    cryptocurrencyPair.setSecondCrypto("BTC");
                    cryptocurrencyPair.setAmount(Double.valueOf(getAmount(parseHuobi.get("data").get(i).get("symbol").textValue())).doubleValue());
                    cryptocurrencyPairs.add(cryptocurrencyPair);
                }else continue;
            }catch (NullPointerException e){
                continue;
            }
        }

        return cryptocurrencyPairs;
    }

    /**
     * @param requestLink Принимает ссылку на GET запрос для получение всех связок
     * @return название всех существующих пар
     */
    public static String getAllCurrencyPairs(String requestLink){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(requestLink, String.class);
    }


    /**
     * <h3>Парсит переданную связь в параментры(Например: btcusdt) из биржи Huobi</h3>
     * @param symbol Имя связки, которую нужно спарсить
     * @return Имя связи и цена
     * @throws JsonProcessingException
     */
    public static String getOneSymbolFromHuobi(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String req = "https://api.huobi.pro/market/trade?symbol=" + symbol;
        String response = restTemplate.getForObject(req, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode obj = objectMapperForMexc.readTree(response);
        return symbol;
    }

    public static String getAmount(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String req = "https://api.huobi.pro/market/trade?symbol=" + symbol;
        String response = restTemplate.getForObject(req, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode obj = objectMapperForMexc.readTree(response);
        return String.valueOf(obj.get("tick").get("data").get(0).get("price"));
    }
}