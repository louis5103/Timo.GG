package com.tools.seoultech.timoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tools.seoultech.timoproject.dto.APIDataResponse;
import com.tools.seoultech.timoproject.dto.AccountDto;

import com.tools.seoultech.timoproject.exception.RiotAPIException;
import com.tools.seoultech.timoproject.service.BasicAPIService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequiredArgsConstructor
public class BasicAPIController {
    private final BasicAPIService bas;

    @GetMapping("/requestAPI0")
    public ResponseEntity<APIDataResponse<AccountDto.Response>> requestAPI(@RequestBody AccountDto.Response dto) throws Exception {
        String requestURL = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/"+ dto.getGameName() +"/"+ dto.getTagLine() +"?api_key=RGAPI-1f999d87-b868-47c8-844f-725abf9cdbb4";
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
        AccountDto.Response response = objectMapper.readValue(httpResponse.body(), AccountDto.Response.class);
        return new ResponseEntity<>(APIDataResponse.of(response), HttpStatus.OK);
    }
    @GetMapping("/requestAPI1")
    public ResponseEntity<APIDataResponse<AccountDto.Response>> requestAPI1(@Validated @RequestBody AccountDto.Request dto) throws RiotAPIException, Exception{
        AccountDto.Response response_dto = bas.findUserAccount(dto);
        return ResponseEntity.status(HttpStatus.OK).body(APIDataResponse.of(response_dto));
    }
}
