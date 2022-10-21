package com.alexander.identityservice.service.impl;

import com.alexander.identityservice.controller.response.TokenResponse;
import com.alexander.identityservice.authencation.jwt.JwtTokenUtil;
import com.alexander.identityservice.model.RefreshToken;
import com.alexander.identityservice.model.User;
import com.alexander.identityservice.repository.RefreshTokenRepository;
import com.alexander.identityservice.repository.UserRepository;
import com.alexander.identityservice.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    @Transactional
    public TokenResponse createTokenResponse(Long userId) {
        var user = userRepository.findById(userId).orElseThrow();
        String refreshToken = jwtTokenUtil.generateRefreshToken(user);
        saveToken(refreshToken, user);
        return createTokenResponse(user, refreshToken);
    }

    @Override
    @Transactional
    public TokenResponse refreshTokens(String username, String refreshToken) {
        var user = userRepository.findByEmail(username);
        RefreshToken entity = refreshTokenRepository.findByToken(refreshToken).orElseThrow(() -> new RuntimeException("Token expired"));
        String newRefreshToken = jwtTokenUtil.generateRefreshToken(user);
        setFields(entity, newRefreshToken, user);
        return createTokenResponse(user, newRefreshToken);
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void deleteRefreshTokensByExpireDate() {
        var now = LocalDateTime.now();
        log.info("Delete refreshTokens by expired date {}", now);
        refreshTokenRepository.deleteByExpireDate(now);
    }

    private TokenResponse createTokenResponse(User user, String refreshToken) {
        var tokenResponse = new TokenResponse();
        tokenResponse.setTokenType("Bearer");
        tokenResponse.setAccessToken(jwtTokenUtil.generateToken(user));
        tokenResponse.setExpiresIn(jwtTokenUtil.extractExpiration(refreshToken).getTime());
        tokenResponse.setRefreshToken(refreshToken);
        return tokenResponse;
    }

    private void saveToken(String refreshToken, User user) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByUserId(user.getId());
        var entity = optionalRefreshToken.orElseGet(RefreshToken::new);
        setFields(entity, refreshToken, user);
        refreshTokenRepository.saveAndFlush(entity);
    }

    private void setFields(RefreshToken entity, String refreshToken, User user) {
        entity.setToken(refreshToken);
        entity.setExpireDate(extractExpiration(refreshToken));
        entity.setUser(user);
    }

    private LocalDateTime extractExpiration(String token) {
        return jwtTokenUtil.extractExpiration(token).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
