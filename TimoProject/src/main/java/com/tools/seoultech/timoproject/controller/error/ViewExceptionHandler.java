package com.tools.seoultech.timoproject.controller.error;

import com.tools.seoultech.timoproject.constant.ErrorCode;
import com.tools.seoultech.timoproject.exception.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@ControllerAdvice
public class ViewExceptionHandler {
    @ExceptionHandler
    public ModelAndView handleException(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus httpStatus = errorCode.isClientSideError() ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;

        return new ModelAndView(
                "error",
                Map.of(
                "errorCode", errorCode.getCode(),
                "httpStatus", errorCode.getHttpStatus(),
                "message", errorCode.getMessage()
        ));
    }
    @ExceptionHandler
    public ModelAndView handleException(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        System.err.println("익셈션 핸들러에서 예외 처리.");
        return new ModelAndView(
                "error",
                Map.of(
                        "errorCode", errorCode.getCode(),
                        "httpStatus", errorCode.getHttpStatus(),
                        "message", errorCode.getMessage()
        ));
    }
}
