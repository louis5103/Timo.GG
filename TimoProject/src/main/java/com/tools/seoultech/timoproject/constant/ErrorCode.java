package com.tools.seoultech.timoproject.constant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    OK(0, ErrorCategory.NORMAL, "Ok"),
    BAD_REQUEST(10000, ErrorCategory.CLIENT_SIDE, "Bad Request"),
    SPRING_BAD_REQUEST(10001, ErrorCategory.CLIENT_SIDE, "Spring detected Bad Request"),
    VALIDATION_ERROR(10002, ErrorCategory.CLIENT_SIDE, "Validation error"),

    INTERNAL_ERROR(3, ErrorCategory.SERVER_SIDE, "Internal Error"),
    SPRING_INTERNAL_ERROR(4, ErrorCategory.SERVER_SIDE, "Spring detected Internal Error"),
    DATA_ACCESS_ERROR(5, ErrorCategory.SERVER_SIDE, "Data Access error"),
    API_ACCESS_ERROR(6, ErrorCategory.RIOT_API_SERVER, "RIOT API ACCESS error");
    private final int code;
    private final ErrorCategory errorCategory;
    private final String message;

    public String getMessage(Exception e) {
        return this.message + e.getMessage();
    }
    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(getMessage());
    }
    public boolean isClientSideError() {
        return this.getErrorCategory() == ErrorCategory.CLIENT_SIDE;
    }
    public boolean isServerSideError() {
        return this.getErrorCategory() == ErrorCategory.SERVER_SIDE;
    }

    public enum ErrorCategory {
        NORMAL, CLIENT_SIDE, SERVER_SIDE, RIOT_API_SERVER
    }
}
