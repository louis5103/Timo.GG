package com.tools.seoultech.timoproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// https://localhost:8080/naver/com
///  https://localhost:8080/

@Controller
@RequestMapping("/naver")
public class testcontrollerview {
//    io: A
    @GetMapping("/io")
    public void io(){
        System.err.println("naver/io가 실행되었습니다");
    }
//    org: B
//    com: C

}
