package com.artmcoder.spreadsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@SpringBootApplication
public class SpreadSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpreadSearchApplication.class, args);
	}

	@Scheduled(fixedDelay = 5000)
	public void scheduleFixedDelayTask() throws IOException {
		String url = "https://api.telegram.org/bot5960679064:AAEzKpKIYKs7uw-R7pxCglQiIFk9qLT5aSk/sendMessage?chat_id=-1001784407940&text=hello";

		URL obj = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

		connection.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		System.out.println(response.toString());
		System.out.println(
				"Fixed delay task - " + System.currentTimeMillis() / 1000);
	}
}
