package com.tools.seoultech.timoproject.controller.error;

import com.tools.seoultech.timoproject.constant.ErrorCode;
import com.tools.seoultech.timoproject.dto.APIErrorResponse;
import com.tools.seoultech.timoproject.exception.RiotAPIException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(annotations = RestController.class)
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
//    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ResponseEntity<Object> handleRiotAPIException(RiotAPIException e, WebRequest request) {
//        return handleExceptionInternal(e, request);
        return getInternalResponseEntity(e, ErrorCode.API_ACCESS_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request){
        ErrorCode errorCode = ErrorCode.valueOf(HttpStatus.valueOf(statusCode.value()));
        return getInternalResponseEntity(ex, errorCode, request);
    }
    private ResponseEntity<Object> getInternalResponseEntity(Exception e, ErrorCode errorcode, WebRequest request){
        HttpStatusCode httpStatusCode = errorcode.getHttpStatus(); // 자동타입변환
        return getInternalResponseEntity(e, errorcode, HttpHeaders.EMPTY, httpStatusCode, request);
    }
    private ResponseEntity<Object> getInternalResponseEntity(Exception e, ErrorCode errorCode, HttpHeaders headers, HttpStatusCode httpStatusCode, WebRequest request) {
        return super.handleExceptionInternal(
                e,
                APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
                headers,
                httpStatusCode,
                request
        );
    }
}
