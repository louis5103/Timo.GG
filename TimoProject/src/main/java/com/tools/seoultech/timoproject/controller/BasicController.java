package com.tools.seoultech.timoproject.controller;

import com.tools.seoultech.timoproject.dto.APIDataResponse;
import com.tools.seoultech.timoproject.dto.AccountDto;
import com.tools.seoultech.timoproject.dto.Detail_MatchInfoDTO;
import com.tools.seoultech.timoproject.exception.GeneralException;
import com.tools.seoultech.timoproject.service.BasicAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class BasicController {
    private final BasicAPIService basicAPIService;

    @GetMapping
    public String goMain(){
        return "index";
    }

    @GetMapping("user")
    public ModelAndView requestAPI(
            String gameName, String tagLine
    ) throws Exception {
        AccountDto.Response response_dto = basicAPIService.findUserAccount(AccountDto.Request.of(gameName, tagLine));
        return new ModelAndView(
                "users/" + gameName,
                Map.of(
                        "puuid", response_dto.getPuuid(),
                        "gameName", response_dto.getGameName(),
                        "tagLine", response_dto.getTagLine())
        );
    }
    @GetMapping("전적검색")
    public String showMatchList(
//            String puuid,
            Model model
    ) throws Exception {
        String puuid = basicAPIService.findUserAccount(AccountDto.Request.of("롤찍먹만할게요","5103")).getPuuid();
        List<String> matchList = basicAPIService.requestMatchList(puuid);
        List<Detail_MatchInfoDTO> dtoList = new ArrayList<>();
        for(String match : matchList){
            dtoList.add(basicAPIService.requestMatchInfo(match));
        }
        model.addAttribute("matchList", dtoList);

//        return ResponseEntity.status(HttpStatus.OK).body(APIDataResponse.of(dtoList));
        return "matches";
    }
    @GetMapping("testList")
    public String showTestList(){
        log.info("testList: redirect=matches");
        return "matches";
    }
}
