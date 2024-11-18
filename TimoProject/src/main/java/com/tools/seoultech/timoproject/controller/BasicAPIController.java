package com.tools.seoultech.timoproject.controller;

import com.tools.seoultech.timoproject.dto.APIDataResponse;
import com.tools.seoultech.timoproject.dto.AccountDto;

import com.tools.seoultech.timoproject.dto.MatchInfoDTO;
import com.tools.seoultech.timoproject.exception.RiotAPIException;
import com.tools.seoultech.timoproject.service.BasicAPIService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BasicAPIController {
    private final BasicAPIService bas;

    @GetMapping("/request/Account")
    public ResponseEntity<APIDataResponse<AccountDto.Response>> requestAccount(
            @Validated @RequestBody AccountDto.Request dto) throws RiotAPIException, Exception
    {
        AccountDto.Response response_dto = bas.findUserAccount(dto);
        return ResponseEntity.status(HttpStatus.OK).body(APIDataResponse.of(response_dto));
    }
    @GetMapping("/request/MatchV5/{matchid}")
    public ResponseEntity<APIDataResponse<MatchInfoDTO>> requestMatchInfo(
            @PathVariable String matchid,
            Model model
    ) throws Exception{
        System.err.println("Controller: "+matchid);
        MatchInfoDTO match_dto = bas.requestMatchInfo(matchid);
        model.addAttribute("match_dto", match_dto);
        return ResponseEntity.status(HttpStatus.OK).body(APIDataResponse.of(match_dto));
    }
}
