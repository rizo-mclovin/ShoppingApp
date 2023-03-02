package com.connor.spreadsearch.api.parsers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.regex.Pattern;

@SuppressWarnings("all")
public class KuCoin {
    public static void main(String[] args) throws IOException {
        ObjectMapper kuCoinMapper = new ObjectMapper();
        JsonNode parseKuCoin = kuCoinMapper.readTree(getAllCurrencyPairs("https://www.mexc.com/open/api/v2/market/ticker"));
        String a = null;
        // Return all pairs with their price
        for(int i = 0; i < 2000; i++) {
            try {
                a = parseKuCoin.get("data").get(i).get("symbol").textValue();
                System.out.println(getOneSymbolFromKuCoin(a.replace("_", "-")));
            }catch (NullPointerException e){
                continue;
            }
        }
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
        return symbol + " - " + obj.get("data").get("asks").get(0).get(0).textValue();
    }
}