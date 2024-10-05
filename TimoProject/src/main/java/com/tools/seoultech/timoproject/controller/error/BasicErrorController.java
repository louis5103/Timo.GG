package com.tools.seoultech.timoproject.controller.error;

import com.tools.seoultech.timoproject.constant.ErrorCode;
import com.tools.seoultech.timoproject.dto.APIErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class BasicErrorController implements ErrorController {
    // View
    @RequestMapping(value = "/error", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView View_error(HttpServletResponse response){
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = ErrorCode.valueOf(httpStatus);
        System.err.println("View error page 연동");
        return new ModelAndView(
                "error",
                Map.of(
                        "errorCode", errorCode.getCode(),
                        "httpStatus", errorCode.getHttpStatus(),
                        "message", errorCode.getMessage()
        ));
    }
    // API
    @RequestMapping("/error")
    public ResponseEntity<APIErrorResponse> API_error(HttpServletResponse response){
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = ErrorCode.valueOf(httpStatus);

        return ResponseEntity.status(httpStatus).body(APIErrorResponse.of(false, errorCode, errorCode.getMessage()));
    }
}
