package com.levelup.levelup_auth.application.port.in;

public record JwtTokenPair(String accessToken, String refreshToken) {
}