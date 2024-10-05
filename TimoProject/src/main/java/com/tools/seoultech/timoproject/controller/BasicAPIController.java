package com.tools.seoultech.timoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tools.seoultech.timoproject.dto.APIDataResponse;
import com.tools.seoultech.timoproject.dto.RequestAccountDto;
import com.tools.seoultech.timoproject.dto.ResponseAccountDto;
//import com.tools.seoultech.timoproject.exception.GeneralException;
//import com.tools.seoultech.timoproject.exception.RiotAPIException;
import com.tools.seoultech.timoproject.exception.RiotAPIException;
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
    public ResponseEntity<APIDataResponse<ResponseAccountDto>> requestAPI(@RequestBody RequestAccountDto dto) throws Exception {
        String requestURL = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/"+ dto.gameName() +"/"+ dto.tagLine() +"?api_key=RGAPI-1f999d87-b868-47c8-844f-725abf9cdbb4";
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(requestURL))
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if(httpResponse.statusCode() == HttpStatus.NOT_FOUND.value()) {
            System.err.println("API 예외 처리 ");
            throw new RiotAPIException("계정 정보 API 호출 실패");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseAccountDto response = objectMapper.readValue(httpResponse.body(), ResponseAccountDto.class);
        return new ResponseEntity<>(APIDataResponse.of(response), HttpStatus.OK);
    }
}
