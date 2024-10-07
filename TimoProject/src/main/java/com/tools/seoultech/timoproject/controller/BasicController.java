package com.tools.seoultech.timoproject.controller;

import com.tools.seoultech.timoproject.dto.AccountDto;
import com.tools.seoultech.timoproject.service.BasicAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

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
}
