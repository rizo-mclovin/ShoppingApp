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
public class Mexc {
    public static void main(String[] args) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.mexc.com/open/api/v2/market/ticker"; // Все связки

        String allSymbolsFromMexc = restTemplate.getForObject(url, String.class);
        ObjectMapper mexcMapper = new ObjectMapper();
        JsonNode parseMexc = mexcMapper.readTree(allSymbolsFromMexc);

        for(int i = 0; i < 2000; i++) {
            System.out.println(parseMexc.get("data").get(i).get("symbol") +" "+ parseMexc.get("data").get(i).get("last"));
        }

        System.out.println(getOneSymbolFromMexc("BTC_USDT")); //  Получаем данные о BTC_USDT из MEXC (name, price)

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