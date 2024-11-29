package com.tools.seoultech.timoproject.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.tools.seoultech.timoproject.exception.RiotAPIException;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;

import java.util.List;

@Getter
@Builder
@Log4j2
@RequiredArgsConstructor
public class MatchInfoDTO {
    private final List<String> participants;
    private final Integer queueId;
    private final String gameMode;
    private final Long gameDuration;
    private final List<UserInfo> userInfo;
//    @com.fasterxml.jackson.databind.annotation.JsonDeserialize(builder = UserInfo.UserInfoBuilder.class)
    @Getter
    @ToString
    @Jacksonized
    public static class UserInfo {
        private final String puuid;
        private final String riotIdGameName;
        private final String riotIdTagLine; // riotIdTagline

        private final String championName;

        private final Integer kills;
        private final Integer deaths;
        private final Integer assists;

        private final Integer totalMinionsKilled;
        private final Integer summoner1Id;
        private final Integer summoner2Id;

        @Size(min=6, max=6)
        private final List<Integer> Items;

            @Size(min=2, max=2)
//            @JsonDeserialize(using=MatchInfoDeserializer.class)
            private final List<Integer> Runes;

        private final Integer teamId;
        private final Boolean win;

        @JsonCreator
        public UserInfo(
                @JsonProperty("puuid") String puuid,
                @JsonProperty("riotIdGameName") String riotIdGameName,
                @JsonProperty("riotIdTagline") String riotIdTagLine,
                @JsonProperty("championName") String championName,
                @JsonProperty("kills") Integer kills,
                @JsonProperty("deaths") Integer deaths,
                @JsonProperty("assists") Integer assists,
                @JsonProperty("totalMinionsKilled") Integer totalMinionsKilled,
                @JsonProperty("summoner1Id") Integer summoner1Id,
                @JsonProperty("summoner2Id") Integer summoner2Id,
                @JsonProperty("item0") Integer item0,
                @JsonProperty("item1") Integer item1,
                @JsonProperty("item2") Integer item2,
                @JsonProperty("item3") Integer item3,
                @JsonProperty("item4") Integer item4,
                @JsonProperty("item5") Integer item5,
                @JsonProperty("item6") Integer item6,
                @JsonProperty("perks") JSONObject perks,
                @JsonProperty("teamId") Integer teamId,
                @JsonProperty("win") Boolean win) {

            this.puuid = puuid;
            this.riotIdGameName = riotIdGameName;
            this.riotIdTagLine = riotIdTagLine;
            this.championName = championName;

            this.kills = kills;
            this.deaths = deaths;
            this.assists = assists;

            this.totalMinionsKilled = totalMinionsKilled;
            this.summoner1Id = summoner1Id;
            this.summoner2Id = summoner2Id;
            this.Items = List.of(item0, item1, item2, item3, item4, item5, item6);

            DocumentContext node = JsonPath.parse(perks);
            Integer mainRune = node.read("$.styles[0].selections[0].perk");
            Integer subRune = node.read("$.styles[1].style");
            this.Runes = List.of(mainRune, subRune);

            this.teamId = teamId;
            this.win = win;
        }
    }

    public static MatchInfoDTO of(String json) {
        Configuration conf = Configuration
                .builder()
                .mappingProvider(new JacksonMappingProvider())
                .build();
        TypeRef<List<UserInfo>> typeRef = new TypeRef<>() {};

        DocumentContext node = JsonPath.using(conf).parse(json);
        MatchInfoDTO testDTO = new MatchInfoDTO(
                node.read("$.metadata.participants"),
                node.read("$.info.queueId"),
                node.read("$.info.gameMode"),
                node.read("$.info.gameDuration", Long.class),

                node.read("$.info.participants.*['puuid', 'riotIdGameName', 'riotIdTagline', 'championName'," +
                        " 'kills', 'deaths', 'assists', 'totalMinionsKilled', 'summoner1Id', 'summoner2Id', " +
                        "'item0', 'item1', 'item2', 'item3', 'item4', 'item5', 'item6', " +
                        "'perks', 'teamId', 'win']", typeRef)
        );
        log.info("BasicAPIService: Completed MatchInfoDTO request");
        return testDTO;
    }

    public UserInfo getMyInfo(String puuid) {
        return userInfo.stream()
                .filter(u -> u.getPuuid().equals(puuid))
                .findFirst()
                .orElseThrow(() -> new RiotAPIException("대전 유저 중에 일치하는 puuid가 없습니다."));
    }
}
