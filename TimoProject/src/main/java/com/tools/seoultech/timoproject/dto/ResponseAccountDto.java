package com.tools.seoultech.timoproject.dto;

public record ResponseAccountDto(
    String puuid,
    String gameName,
    String tagLine
) {
    public static ResponseAccountDto of(String puuid, String gameName, String tagLine){
        return new ResponseAccountDto(puuid, gameName, tagLine);
    }
}
