package com.levelup.levelup_auth.presentation.rest.dto.response;

import com.levelup.levelup_auth.application.port.in.JwtTokenPair;

public record TokenResponse(String accessToken, String refreshToken) {
    public static TokenResponse from(JwtTokenPair jwtTokenPair) {
        return new TokenResponse( jwtTokenPair.accessToken(), jwtTokenPair.refreshToken() );
    }
}
