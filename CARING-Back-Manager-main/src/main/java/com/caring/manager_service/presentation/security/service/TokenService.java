package com.caring.manager_service.presentation.security.service;

import com.caring.manager_service.presentation.security.vo.JwtToken;
import org.springframework.security.core.Authentication;

public interface TokenService {
    JwtToken login(String memberCode, String password);

    JwtToken reissueToken(String refreshToken);

    JwtToken generateToken(Authentication authentication);

    Authentication getAuthentication(String accessToken);

    boolean logout(String refreshToken);

    boolean existsRefreshToken(String refreshToken);
}
