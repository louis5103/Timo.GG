package com.tools.seoultech.timoproject.dto;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.tools.seoultech.timoproject.constant.DDragonSpellCode;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.minidev.json.JSONArray;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@RequiredArgsConstructor
public class Detail_MatchInfoDTO {
    private final String icon;
    private final Boolean win;

    private final Integer kills;
    private final Integer deaths;
    private final Integer assists;

    private final String summoner1Id; // TODO: id to url img
    private final String summoner2Id; // TODO: "
    private final String rune3; // TODO: "
    private final String rune4; // TODO: "

    private final List<String> items; // TODO: 0번 아이템 비즈니스 로직

    private final Integer totalMinionskilled;
    private final List<String> participants; // TODO: puuid to riotIdGameName

    public static Detail_MatchInfoDTO of(MatchInfoDTO matchInfoDTO, String puuid, String runeJson){
        MatchInfoDTO.UserInfo myInfo = matchInfoDTO.getMyInfo(puuid);
        DocumentContext node = JsonPath.parse(runeJson);
        String jsonPath1 = String.format("$[*].slots[0]..runes[?(@.id==%d)]['icon']", myInfo.getRunes().get(0));
        String jsonPath2 = String.format("$..[*][?(@.id==%d)]['icon']", myInfo.getRunes().get(1));

        String iconURL = String.format("https://ddragon.leagueoflegends.com/cdn/14.23.1/img/champion/%s.png", myInfo.getChampionName());
        List<String> itemURL = myInfo.getItems().stream()
                .map(id -> (id>0) ? String.format("https://ddragon.leagueoflegends.com/cdn/14.23.1/img/item/%d.png", id) : null)
                .collect(Collectors.toList());
        String spell1URL = String.format("https://ddragon.leagueoflegends.com/cdn/14.23.1/img/spell/%s",
                DDragonSpellCode.of(myInfo.getSummoner1Id()));
        String spell2URL = String.format("https://ddragon.leagueoflegends.com/cdn/14.23.1/img/spell/%s.png",
                DDragonSpellCode.of(myInfo.getSummoner2Id()));
        String mainRuneURL = String.format("https://ddragon.leagueoflegends.com/cdn/img/%s", ((JSONArray)node.read(jsonPath1)).getFirst());
        String subRuneURL = String.format("https://ddragon.leagueoflegends.com/cdn/img/%s", ((JSONArray)node.read(jsonPath2)).getFirst());
        List<String> participants = matchInfoDTO.getUserInfo()
                .stream()
                .map(user -> {return user.getRiotIdGameName();})
                .collect(Collectors.toList());
        Detail_MatchInfoDTO var =  Detail_MatchInfoDTO.builder()
                .icon(iconURL)
                .win(myInfo.getWin())
                .kills(myInfo.getKills())
                .deaths(myInfo.getDeaths())
                .assists(myInfo.getAssists())
                .summoner1Id(spell1URL)
                .summoner2Id(spell2URL)
                .rune3(mainRuneURL)
                .rune4(subRuneURL)
                .items(itemURL)
                .participants(participants)
                .build();
        return var;
    }
}



