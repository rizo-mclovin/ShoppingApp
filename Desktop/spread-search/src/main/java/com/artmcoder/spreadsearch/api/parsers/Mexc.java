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
public class Mexc {

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


    public List<CryptocurrencyPair> usdtParser() throws IOException{
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.mexc.com/open/api/v2/market/ticker"; // Все связки

        String allSymbolsFromMexc = restTemplate.getForObject(url, String.class);
        ObjectMapper mexcMapper = new ObjectMapper();
        JsonNode parseMexc = mexcMapper.readTree(allSymbolsFromMexc);

        for(int i = 0; i < 2000; i++) {
            CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
            cryptocurrencyPair.setExchange("Mexc");
            String firstCryptoName = parseMexc.get("data").get(i).get("symbol").textValue();

            boolean secondCryptoIsUSDT = firstCryptoName.indexOf("_USDT") != -1;
            if (secondCryptoIsUSDT == true){
                cryptocurrencyPair.setFirstCrypto(firstCryptoName.replace("_USDT", ""));
                cryptocurrencyPair.setAmount(Double.valueOf(parseMexc.get("data").get(i).get("last").textValue()));
                cryptocurrencyPair.setSecondCrypto("USDT");
                cryptocurrencyPairs.add(cryptocurrencyPair);
            }
            else continue;
        }
        return cryptocurrencyPairs;
    }


    public List<CryptocurrencyPair> btcParser() throws IOException{
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.mexc.com/open/api/v2/market/ticker"; // Все связки

        String allSymbolsFromMexc = restTemplate.getForObject(url, String.class);
        ObjectMapper mexcMapper = new ObjectMapper();
        JsonNode parseMexc = mexcMapper.readTree(allSymbolsFromMexc);

        for(int i = 0; i < 2000; i++) {
            CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
            cryptocurrencyPair.setExchange("Mexc");
            String firstCryptoName = parseMexc.get("data").get(i).get("symbol").textValue();

            boolean isExistBTC = firstCryptoName.indexOf("_BTC") != -1;
            if (isExistBTC == true){
                cryptocurrencyPair.setFirstCrypto(firstCryptoName.replace("_BTC", ""));
                cryptocurrencyPair.setAmount(Double.valueOf(parseMexc.get("data").get(i).get("last").textValue()));
                cryptocurrencyPair.setSecondCrypto("BTC");
                cryptocurrencyPairs.add(cryptocurrencyPair);
            }
            else continue;
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
    * <h3>Парсит переданную связь в параментры(Например: BTC_USDT) из биржи MEXC</h3>
    * @param symbol Имя связки, которую нужно спарсить
    * @return Имя связи и цена
    * @throws JsonProcessingException
    */
    public static String getOneSymbolFromMexc(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String req = "https://contract.mexc.com/api/v1/contract/fair_price/" + symbol;
        String response = restTemplate.getForObject(req, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode obj = objectMapperForMexc.readTree(response);
        return obj.get("data").get("symbol") +" "+ obj.get("data").get("fairPrice");
    }
}