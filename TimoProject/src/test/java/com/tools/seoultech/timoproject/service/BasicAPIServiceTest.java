package com.tools.seoultech.timoproject.service;


import com.tools.seoultech.timoproject.dto.AccountDto;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Riot_API_Test")
@SpringBootTest
class BasicAPIServiceTest {
    @Autowired private BasicAPIService bas;

    @DisplayName("[API] puuid 검색 - 잘못된 요청")
    @Test
    public void test_void_test() throws Exception {
        ConstraintViolationException e = new ConstraintViolationException(Set.of());
//        BDDMockito.given(bas.findUserAccount(AccountDto.Request.of(null, null)))
//                .willThrow(e);
        Throwable thrown = Assertions.catchThrowable(() -> bas.findUserAccount(AccountDto.Request.of(null, null)));

        assertThat(thrown)
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(e.getMessage());
    }

    @DisplayName("[API] puuid 검색 - 정상 요청")
    @Test
    public void givenNothing_whenSearchingUser_thenReturnAPIErrorResponse() throws Exception{
        //given
        String puuid = "-O2mxHCCLutqV-VC6FZzTTDDDF-QlfsGlR9qP7Cwb4E7ujIzdRhrtM5ibhPlXshnx3ehrbxD01crbQ";
        String name = "롤찍먹만할게요";
        String tag = "5103";

        //when
        AccountDto.Response response_dto = bas.findUserAccount(AccountDto.Request.of(name, tag));
        System.out.println(response_dto);

        //then
        assertThat(response_dto)
                .hasFieldOrPropertyWithValue("puuid", puuid)
                .hasFieldOrPropertyWithValue("gameName", name)
                .hasFieldOrPropertyWithValue("tagLine", tag);

    }
}