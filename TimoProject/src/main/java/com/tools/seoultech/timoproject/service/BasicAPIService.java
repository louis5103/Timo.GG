package com.tools.seoultech.timoproject.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tools.seoultech.timoproject.constant.ErrorCode;
import com.tools.seoultech.timoproject.dto.AccountDto;
import com.tools.seoultech.timoproject.exception.RiotAPIException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Validated
@Slf4j
public class BasicAPIService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private HttpRequest request;
    private HttpResponse<String> response;

    @Value("${api_key}") private String api_key;

    @Transactional
    public AccountDto.Response findUserAccount(@Valid AccountDto.Request dto) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/")
                .append(dto.getGameName()).append("/")
                .append(dto.getTagLine()).append("/")
                .append("?api_key=").append(api_key);

        request = HttpRequest.newBuilder()
                .uri(URI.create(sb.toString()))
                .GET()
                .build();
        response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString());

        riotAPI_validation(response);
        return mapper.readValue(response.body(), AccountDto.Response.class);
    }

    private void riotAPI_validation(HttpResponse<String> response){
        if(response.statusCode() == HttpStatus.NOT_FOUND.value()) {
            log.info("riot API 예외 처리 - 사용자를 찾을 수 없습니다.");
            throw new RiotAPIException("계정 정보 API 호출 실패 - 사용자 정보가 없습니다.", ErrorCode.API_ACCESS_ERROR);
        }
        if(response.statusCode() == HttpStatus.UNAUTHORIZED.value()) {
            log.info("riot API 예외 처리 - API_KEY가 유효하지 않습니다.");
            throw new RiotAPIException("유효하지 않은 API_KEY", ErrorCode.API_ACCESS_ERROR);
        }
    }
}
