package com.tools.seoultech.timoproject.controller;

import com.tools.seoultech.timoproject.dto.RequestAccountDto;
import com.tools.seoultech.timoproject.dto.ResponseAccountDto;
import org.apache.coyote.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
@RequestMapping("/")
public class BasicController {
    @GetMapping
    public String goMain(){
        return "index";
    }


}
