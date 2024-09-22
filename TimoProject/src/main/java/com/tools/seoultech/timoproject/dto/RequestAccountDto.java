package com.tools.seoultech.timoproject.dto;

public record RequestAccountDto(
    String gameName,
    String tagLine
) {
    public static RequestAccountDto of(String gameName, String tagLine){
        return new RequestAccountDto(gameName, tagLine);
    }
}
