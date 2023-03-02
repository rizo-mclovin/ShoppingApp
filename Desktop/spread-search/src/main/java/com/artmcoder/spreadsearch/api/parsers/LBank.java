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
public class LBank {

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
     * @return All Cryptocurrencys with USDT
     * @throws IOException
     */
    public List<CryptocurrencyPair> usdtParser() throws  IOException{
        ObjectMapper lbankMapper = new ObjectMapper();
        JsonNode parseLbank= lbankMapper.readTree(getAllCurrencyPairs("https://api.lbkex.com/v2/currencyPairs.do"));
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();

        // Return all pairs with their price //886
        for(int i = 0; i < 886; i++) {
            CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
            try {
//
                String firstCryptoName = getOneSymbolFromLbank(parseLbank.get("data").get(i).textValue());
                boolean isExistUSDT = firstCryptoName.indexOf("_usdt") != -1;
                if (isExistUSDT == true){
                    cryptocurrencyPair.setExchange("LBank");
                    cryptocurrencyPair.setFirstCrypto(firstCryptoName.replaceAll("^\"|\"$", "").replace("_usdt", "").toUpperCase());
                    cryptocurrencyPair.setSecondCrypto("USDT");
                    cryptocurrencyPair.setAmount(Double.valueOf(getAmount(parseLbank.get("data").get(i).textValue())));
                    cryptocurrencyPairs.add(cryptocurrencyPair);
                }else continue;



            }catch (NullPointerException e){
                continue;
            }
        }
        return cryptocurrencyPairs;
    }


    /**
     * @return All Cryptocurrencys with BTC
     * @throws IOException
     */
    public List<CryptocurrencyPair> btcParser() throws  IOException{
        ObjectMapper lbankMapper = new ObjectMapper();
        JsonNode parseLbank= lbankMapper.readTree(getAllCurrencyPairs("https://api.lbkex.com/v2/currencyPairs.do"));
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();

        // Return all pairs with their price //886
        for(int i = 0; i < 886; i++) {
            CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
            try {
//
                String firstCryptoName = getOneSymbolFromLbank(parseLbank.get("data").get(i).textValue());
                boolean isExistBTC = firstCryptoName.indexOf("_btc") != -1;
                if (isExistBTC == true){
                    cryptocurrencyPair.setExchange("LBank");
                    cryptocurrencyPair.setFirstCrypto(firstCryptoName.replaceAll("^\"|\"$", "").replace("_btc", "").toUpperCase());
                    cryptocurrencyPair.setSecondCrypto("BTC");
                    cryptocurrencyPair.setAmount(Double.valueOf(getAmount(parseLbank.get("data").get(i).textValue())));
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
    * <h3>Парсит переданную связь в параментры(Например: btc_usdt) из биржи LBank</h3>
    * @param symbol Имя связки, которую нужно спарсить
    * @return Имя связи и цена
    * @throws JsonProcessingException
    */
    public static String getOneSymbolFromLbank(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String fuckingRequest = "https://api.lbkex.com/v2/ticker/24hr.do?symbol=" + symbol;
        String fuckingResponse = restTemplate.getForObject(fuckingRequest, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode object = objectMapperForMexc.readTree(fuckingResponse);
        return String.valueOf(object.get("data").get(0).get("symbol"));
    }

    /**
     * @param symbol Имя связки
     * @return Цена связки, переданная в параметры
     * @throws JsonProcessingException
     */
    public static String getAmount(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String request = "https://api.lbkex.com/v2/ticker/24hr.do?symbol=" + symbol;
        String response = restTemplate.getForObject(request, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode object = objectMapperForMexc.readTree(response);
        return String.valueOf(object.get("data").get(0).get("ticker").get("latest").textValue());
    }
}