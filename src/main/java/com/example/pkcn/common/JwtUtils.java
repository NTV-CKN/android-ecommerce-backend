package com.example.pkcn.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${app.jwt.secretkey}")
    private String jwtSecretKey;

    @Value("${app.jwt.expire.access.token}")
    private long jwtExpire;

    @Value("${app.jwt.expire.refresh.token}")
    private long jwtRefreshExpire;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    public String generateAccessToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpire);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtRefreshExpire);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            System.err.println("Chữ ký JWT không hợp lệ! Token có dấu hiệu bị sửa đổi cấu trúc: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Định dạng chuỗi JWT không hợp lệ (không đủ 3 thành phần dấu chấm): " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("Token đã quá hạn sử dụng rồi! Kích hoạt cơ chế đổi token: " + e.getMessage());
            // Bạn có thể để văng lỗi hoặc giữ return false để tầng Filter xử lý ném 401 như đã thảo luận
        } catch (UnsupportedJwtException e) {
            System.err.println("Cấu trúc mã hóa JWT này không được hệ thống hỗ trợ: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Chuỗi ký tự JWT bị rỗng hoặc không chứa dữ liệu: " + e.getMessage());
        }
        return false;
    }
}