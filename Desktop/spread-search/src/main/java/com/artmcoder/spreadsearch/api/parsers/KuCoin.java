package com.artmcoder.spreadsearch.api.parsers;
import com.artmcoder.spreadsearch.api.models.CryptocurrencyPair;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("all")
public class KuCoin {
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


    /**
     * @return <h3>All Cryptocurrencys with USDT </h3>
     * @throws JsonProcessingException
     */
    public List<CryptocurrencyPair> usdtParser() throws JsonProcessingException {
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();
        ObjectMapper kuCoinMapper = new ObjectMapper();
        JsonNode parseKuCoin = kuCoinMapper.readTree(getAllCurrencyPairs("https://www.mexc.com/open/api/v2/market/ticker"));
        String kuCoinExchangeName = null;
        // Return all pairs with their price //2000
        for(int i = 0; i < 2000; i++) {
            CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
            try {
                kuCoinExchangeName = parseKuCoin.get("data").get(i).get("symbol").textValue();
                cryptocurrencyPair.setExchange("KuCoin");

                boolean isExistUSDT = kuCoinExchangeName.indexOf("_USDT") != -1;
                if (isExistUSDT == true) {
                    String firstCrypto = getOneSymbolFromKuCoin(kuCoinExchangeName.replace("_", "-"));
                    cryptocurrencyPair.setFirstCrypto(firstCrypto.replace("-USDT", ""));
                    cryptocurrencyPair.setSecondCrypto("USDT");
                    cryptocurrencyPair.setAmount(Double.valueOf(getAmount(kuCoinExchangeName.replace("_", "-"))));
                    cryptocurrencyPairs.add(cryptocurrencyPair);
                }else continue;
            }catch (NullPointerException e){
                continue;
            }
        }


        return cryptocurrencyPairs;
    }

    /**
     * @return <h3>All Cryptocurrencys with BTC </h3>
     * @throws JsonProcessingException
     */

    public List<CryptocurrencyPair> btcParser() throws JsonProcessingException {
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();
        ObjectMapper kuCoinMapper = new ObjectMapper();
        JsonNode parseKuCoin = kuCoinMapper.readTree(getAllCurrencyPairs("https://www.mexc.com/open/api/v2/market/ticker"));
        String kuCoinExchangeName = null;
        // Return all pairs with their price //2000
        for(int i = 0; i < 2000; i++) {
            CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
            try {
                kuCoinExchangeName = parseKuCoin.get("data").get(i).get("symbol").textValue();
                cryptocurrencyPair.setExchange("KuCoin");
                boolean isExistBtc = kuCoinExchangeName.indexOf("_BTC") != -1;
                if (isExistBtc == true){
                    String firstCrypto = getOneSymbolFromKuCoin(kuCoinExchangeName.replace("_", "-"));
                    cryptocurrencyPair.setFirstCrypto(firstCrypto.replace("-BTC", ""));
                    cryptocurrencyPair.setSecondCrypto("BTC");
                    cryptocurrencyPair.setAmount(Double.valueOf(getAmount(kuCoinExchangeName.replace("_", "-"))));
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
    * <h3>Парсит переданную связь в параментры(Например: btc_usdt) из биржи KuCoin</h3>
    * @param symbol Имя связки, которую нужно спарсить
    * @return Имя связки и цена
    * @throws JsonProcessingException
    */
    public static String getOneSymbolFromKuCoin(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String req = "https://api.kucoin.com/api/v1/market/orderbook/level2_20?symbol=" + symbol;
        String response = restTemplate.getForObject(req, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode obj = objectMapperForMexc.readTree(response);
        return symbol;
    }

    /**
     * @param symbol Cryptocurrency name
     * @return Price of cryptocurrency
     * @throws JsonProcessingException
     */
    public static String getAmount(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String req = "https://api.kucoin.com/api/v1/market/orderbook/level2_20?symbol=" + symbol;
        String response = restTemplate.getForObject(req, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode obj = objectMapperForMexc.readTree(response);
        return obj.get("data").get("asks").get(0).get(0).textValue();
    }

}