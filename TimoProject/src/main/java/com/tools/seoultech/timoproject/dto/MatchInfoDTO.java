package com.tools.seoultech.timoproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.tools.seoultech.timoproject.exception.GeneralException;
import com.tools.seoultech.timoproject.exception.RiotAPIException;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class MatchInfoDTO {
    private final List<String> participants;
    private final Integer queueId;
    private final String gameMode;
    private final Long gameDuration;
    private final List<UserInfo> userInfo;

    @Getter
    @ToString
    @RequiredArgsConstructor
    @Builder
    @Jacksonized
    private static class UserInfo{
        @JsonProperty("puuid") private final String puuid;
        @JsonProperty("riotIdGameName") final String riotIdGameName;
        @JsonProperty("riotIdTagline") private final String riotIdTagLine; // riotIdTagline

        @JsonProperty("championName") private final String championName;

        @JsonProperty("kills") private final Integer kills;
        @JsonProperty("deaths") final Integer deaths;
        @JsonProperty("assists") final Integer assists;

        @JsonProperty("teamId") final Integer teamId;
        @JsonProperty("win") final Boolean win;

        public static UserInfo of(String json){
            DocumentContext node = JsonPath.parse(json);
            return new UserInfo(
                    node.read("$.puuid"),
                    node.read("$.riotIdGameName"),
                    node.read("$.riotIdTagLine"),
                    node.read("$.championName"),
                    node.read("$.assists"),
                    node.read("$.kills"),
                    node.read("$.deaths"),
                    node.read("$.teamId"),
                    node.read("$.win")
            );
        }
    }

    public static MatchInfoDTO of(String json) {
        ObjectMapper mapper = new ObjectMapper();

        Configuration conf = Configuration
                .builder()
                .mappingProvider(new JacksonMappingProvider())
                .build();
        TypeRef<List<UserInfo>> typeRef = new TypeRef<>() {};

        DocumentContext node = JsonPath.using(conf).parse(json);

        List<UserInfo> test = node.read("$.info.participants.*['puuid', 'riotIdGameName', 'riotIdTagline', 'championName', 'kills', 'deaths', 'assists', 'teamId', 'win']", typeRef);
        System.err.println("test: "+test);
        MatchInfoDTO testDTO = new MatchInfoDTO(
                node.read("$.metadata.participants"),
                node.read("$.info.queueId"),
                node.read("$.info.gameMode"),
                node.read("$.info.gameDuration", Long.class),
                node.read("$.info.participants.*['puuid', 'riotIdGameName', 'riotIdTagline', 'championName', 'kills', 'deaths', 'assists', 'teamId', 'win']")
        );
        return testDTO;
    }

    public UserInfo getMyInfo(String puuid) {
        return userInfo.stream()
                .filter(u -> u.getPuuid().equals(puuid))
                .findFirst()
                .orElseThrow(() -> new RiotAPIException("대전 유저 중에 일치하는 puuid가 없습니다."));
    }
}
