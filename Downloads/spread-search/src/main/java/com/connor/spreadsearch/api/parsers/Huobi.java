package com.connor.spreadsearch.api.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

/**
* @author John
*/

@SuppressWarnings("all")
public class Huobi {
    public static void main(String[] args) throws IOException {
        ObjectMapper huobiMapper = new ObjectMapper();
        JsonNode parseHuobi= huobiMapper.readTree(getAllCurrencyPairs("https://api.huobi.pro/market/tickers"));

        // Return all pairs with their price
        for(int i = 0; i < 955; i++) {
            try {
                getOneSymbolFromHuobi(parseHuobi.get("data").get(i).get("symbol").textValue());
            }catch (NullPointerException e){
                continue;
            }
        }

//                System.out.println(getOneSymbolFromHuobi("btcusdt"));  // Информация о BTC-USDT (name, price)

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
        return symbol + " - " + obj.get("tick").get("data").get(0).get("price");
    }
}