package com.alexander.identityservice.service;

import com.alexander.identityservice.controller.response.TokenResponse;

public interface TokenService {

    TokenResponse createTokenResponse(Long userId);

    TokenResponse refreshTokens(String username, String refreshToken);

}
