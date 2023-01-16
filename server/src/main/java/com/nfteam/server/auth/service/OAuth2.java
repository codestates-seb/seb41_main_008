package com.nfteam.server.auth.service;

import com.nfteam.server.dto.response.auth.SocialLoginResponse;

public interface OAuth2 {

    SocialLoginResponse proceedLogin(String code);

}
