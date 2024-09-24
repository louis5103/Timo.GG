package com.tools.seoultech.timoproject.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tools.seoultech.timoproject.dto.ResponseAccountDto;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class BasicAPIService {
    @Value("${api_key}") private String api_key;

    @Transactional
    public ResponseAccountDto findUserAccount(String gameName, String tagLine) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/")
                .append(gameName).append("/")
                .append(tagLine).append("/")
                .append("?api_key=").append(api_key);

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(sb.toString()))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(
                httpRequest,
                HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        ResponseAccountDto accountDto = mapper.readValue(response.body(), ResponseAccountDto.class);
        return accountDto;
    }
}
