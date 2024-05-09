package com.von.api.common.component.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.von.api.user.model.UserDTO;
import com.von.api.user.repository.UserRepository;

@Log4j2
@Component
public class JwtProvider  {
 
    
    @Value("${jwt.iss}")
    private String issuer;

    private final SecretKey secretKey;
    Instant expiredDate = Instant.now().plus(1, ChronoUnit.DAYS);


    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }


    public String createToken(UserDTO dto) {

        String token = Jwts.builder()
        .issuer(issuer)
        .signWith(secretKey)
        .expiration(Date.from(expiredDate))
        .subject("von")
        .claim("username", dto.getUsername())
        .claim("job", dto.getJob())
        .claim("userId", dto.getId()) //?
        .compact();
        ;

        log.info("로그인 성공으로 발급된 토큰 : "+token);

        return token;
    }


    public String extractTokenFromHeader(HttpServletRequest request) {
        log.info("프론트에서 넘어온 Request 값 : {}"+ request.getServletPath());
        String bearerToken = request.getHeader("Authorization");
        log.info("프론트에서 넘어온 토큰 값 : {}"+ bearerToken);
        return bearerToken != null && bearerToken.startsWith("Bearer ") ? bearerToken.substring(7) : "undefined";
    }
    
    public void printPayload(String accessToken) {
        String[] chunks = accessToken.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        log.info("Token Header : " + header);
        log.info("Token payload : " + payload);

      

    }


    public Claims getPayload(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }
  
    
    
}