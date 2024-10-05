package com.tools.seoultech.timoproject.controller;

import com.tools.seoultech.timoproject.dto.RequestAccountDto;
import com.tools.seoultech.timoproject.dto.ResponseAccountDto;
import com.tools.seoultech.timoproject.service.BasicAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
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

    @GetMapping("requestAPI")
    public ModelAndView requestAPI(
            String gameName,
            String tagLine
    ) throws Exception {
        ResponseAccountDto dto = basicAPIService.findUserAccount(gameName, tagLine);
        return new ModelAndView(
                "apiView",
                Map.of(
                        "puuid", dto.puuid(),
                        "gameName", dto.gameName(),
                        "tagLine", dto.tagLine())
        );
    }
}
