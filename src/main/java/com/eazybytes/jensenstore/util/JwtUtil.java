package com.eazybytes.jensenstore.util;

import com.eazybytes.jensenstore.constants.ApplicationConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final Environment env;

    public String generateJwtToken(Authentication authentication) {
        String jwt = "";

        // 1. 讀取密鑰（優先使用環境變數）
        String secret = env.getProperty(
                ApplicationConstants.JWT_SECRET_KEY,
                ApplicationConstants.JWT_SECRET_DEFAULT_VALUE
        );

        // 2. 將密鑰轉換為 SecretKey 物件
        SecretKey secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

        // 3. 從 Authentication 中提取使用者資訊
        User fetchedUser = (User) authentication.getPrincipal();

        // 4. 建立 JWT Token
        jwt = Jwts.builder()
                .issuer("Eazy Store")                          // 發行者
                .subject("JWT Token")                          // 主旨
                .claim("username", fetchedUser.getUsername())  // 自訂聲明
                .issuedAt(new Date())                          // 發行時間
                .expiration(new Date(new Date().getTime() + 60 * 60 * 1000))  // 過期時間：60分鐘
                .signWith(secretKey)                           // 使用密鑰簽署
                .compact();                                    // 生成最終 Token

        return jwt;
    }
}
