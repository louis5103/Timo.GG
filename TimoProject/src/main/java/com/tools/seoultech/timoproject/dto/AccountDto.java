package com.tools.seoultech.timoproject.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class AccountDto {
    @Getter
    public static class Request{
        @NotBlank @JsonProperty("gameName") private final String gameName;
        @NotBlank @JsonProperty("tagLine") private final String tagLine;

        @JsonCreator
        public Request(@JsonProperty("gameName") String gameName,
                       @JsonProperty("tagLine") String tagLine) {
            this.gameName = gameName;
            this.tagLine = tagLine;
        }

        public static AccountDto.Request of(String gameName, String tagLine) {
            return new AccountDto.Request(gameName, tagLine);
        }
    }

    @Getter
    public static class Response {
        @NotBlank @JsonProperty("puuid") private final String puuid;
        @NotBlank @JsonProperty("gameName") private final String gameName;
        @NotBlank @JsonProperty("tagLine") private final String tagLine;

        @JsonCreator
        public Response(@JsonProperty("puuid") String puuid,
                        @JsonProperty("gameName") String gameName,
                        @JsonProperty("tagLine") String tagLine) {
            this.puuid = puuid;
            this.gameName = gameName;
            this.tagLine = tagLine;
        }

        public static AccountDto.Response of(String puuid, String gameName, String tagLine) {
            return new AccountDto.Response(puuid, gameName, tagLine);
        }
    }
}
