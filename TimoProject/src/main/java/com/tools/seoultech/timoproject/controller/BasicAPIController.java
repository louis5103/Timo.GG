package com.tools.seoultech.timoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tools.seoultech.timoproject.dto.RequestAccountDto;
import com.tools.seoultech.timoproject.dto.ResponseAccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
public class BasicAPIController {
    @GetMapping("/requestAPI0")
    public ResponseEntity<ResponseAccountDto> requestAPI(@RequestBody RequestAccountDto dto) throws Exception{
        String requestURL = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/"+ dto.gameName() +"/"+ dto.tagLine() +"?api_key=RGAPI-1f999d87-b868-47c8-844f-725abf9cdbb4";
        HttpClient httpClient = HttpClient.newHttpClient();
//        getRequest.addHeader("x-api-key", RestTestCommon.API_KEY);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(requestURL))
//                .header("x-api-key", "RGAPI-1f999d87-b868-47c8-844f-725abf9cdbb4")
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse.body());
        System.out.println(httpResponse.statusCode());
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseAccountDto response = objectMapper.readValue(httpResponse.body(), ResponseAccountDto.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
