package com.tools.seoultech.timoproject.controller;

import com.tools.seoultech.timoproject.dto.APIDataResponse;
import com.tools.seoultech.timoproject.dto.AccountDto;

import com.tools.seoultech.timoproject.dto.Detail_MatchInfoDTO;
import com.tools.seoultech.timoproject.dto.MatchInfoDTO;
import com.tools.seoultech.timoproject.exception.RiotAPIException;
import com.tools.seoultech.timoproject.service.BasicAPIService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @GetMapping("/request/MatchV5/matches/전적검색")
    public ResponseEntity<APIDataResponse<List<Detail_MatchInfoDTO>>> requestMatchInfo(
//            @PathVariable String matchid,
            Model model
    ) throws Exception{
        String puuid = bas.findUserAccount(AccountDto.Request.of("롤찍먹만할게요","5103")).getPuuid();
        List<String> matchList = bas.requestMatchList(puuid);
        List<Detail_MatchInfoDTO> dto_List = new ArrayList<>();
        for(String match : matchList){
            Detail_MatchInfoDTO match_dto = bas.requestMatchInfo(match);
            dto_List.add(match_dto);
        }
        model.addAttribute("match_dto", dto_List);
        return ResponseEntity.status(HttpStatus.OK).body(APIDataResponse.of(dto_List));
    }
    @GetMapping("/request/MatchV5/matches/by-puuid/{puuid}")
    public ResponseEntity<APIDataResponse<List<String>>> requestMatchList(
            @PathVariable String puuid
    ) throws Exception{
        System.err.println("Controller: " + puuid);
        List<String> list = bas.requestMatchList(puuid);
        return ResponseEntity.status(HttpStatus.OK).body(APIDataResponse.of(list));
    }
}
