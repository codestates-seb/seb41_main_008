package com.nfteam.server.auth.controller;

import com.nfteam.server.auth.service.AuthService;
import com.nfteam.server.auth.service.OAuth2Service;
import com.nfteam.server.domain.member.entity.Member;
import com.nfteam.server.domain.member.entity.MemberPlatform;
import com.nfteam.server.dto.response.auth.SocialLoginResponse;
import com.nfteam.server.dto.response.cart.CartResponse;
import com.nfteam.server.support.ControllerTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Transactional
class AuthControllerTest extends ControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    private OAuth2Service oAuth2Service;
    @MockBean
    private AuthService authService;

    @Test
    void oauthLogin_test() throws Exception {

        // given
        String socialType = "google";
        String token = "googleToken";

        String accessToken = "Bearer accessToken";
        String refreshToken = "refreshToken";

        Member member = new Member("google@google.com", "nickname", MemberPlatform.GOOGLE);
        member.updateId(1L);
        SocialLoginResponse socialLoginResponse
                = new SocialLoginResponse(accessToken, refreshToken, member);

        given(oAuth2Service.login(token, MemberPlatform.valueOf(socialType.toUpperCase())))
                .willReturn(socialLoginResponse);

        CartResponse cartResponse = new CartResponse(1L);

        given(cartService.loadOwnCart(member.getEmail()))
                .willReturn(cartResponse);

        // When
        ResultActions actions = mockMvc.perform(
                get("/auth/login/{socialType}", socialType)
                        .header("googleToken", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(member.getMemberId()))
                .andExpect(jsonPath("$.email").value(member.getEmail()))
                .andExpect(jsonPath("$.lastLoginTime").value(socialLoginResponse.getLoginResponse().getLastLoginTime()))
                .andExpect(jsonPath("$.profileImageName").value(member.getProfileImageName()))
                .andExpect(jsonPath("$.cartId").value(cartResponse.getCartId()))
                .andDo(document("oauth-login",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("socialType").description("소셜 로그인 플랫폼")
                        ),
                        requestHeaders(
                                headerWithName("googleToken").description("구글 엑세스 토큰")
                        ),
                        responseHeaders(
                                headerWithName("Authorization").description("엑세스 토큰"),
                                headerWithName("RefreshToken").description("리프레시 토큰")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("role").type(JsonFieldType.STRING).description("회원 권한"),
                                        fieldWithPath("lastLoginTime").type(JsonFieldType.STRING).description("마지막 로그인 시간"),
                                        fieldWithPath("profileImageName").type(JsonFieldType.STRING).description("프로필 이미지명"),
                                        fieldWithPath("cartId").type(JsonFieldType.NUMBER).description("회원 카트 식별자")
                                )
                        )
                ));
    }

    @Test
    void logout_test() throws Exception {
        // given
        doNothing().when(authService).logout(Mockito.anyString());

        // When
        ResultActions actions = mockMvc.perform(
                get("/auth/logout")
                        .header("RefreshToken", "refreshToken")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
                .andDo(document("auth-logout",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestHeaders(
                                headerWithName("RefreshToken").description("리프레시 토큰")
                        )
                ));
    }

    @Test
    @WithMockUser
    public void reissue_test() throws Exception {
        // given
        String refreshToken = "refreshToken";
        String accessToken = "accessToken";

        given(authService.reissue(refreshToken)).willReturn(accessToken);

        // when
        ResultActions actions = mockMvc.perform(get("/auth/reissue")
                .header("RefreshToken", "refreshToken")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
                .andDo(document("auth-reissue",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestHeaders(
                                headerWithName("RefreshToken").description("리프레시 토큰")
                        ),
                        responseHeaders(
                                headerWithName("Authorization").description("갱신된 엑세스 토큰")
                        )
                ));
    }

}