package com.ohho.valetparking.global.security;


import com.ohho.valetparking.domains.member.entity.SignIn;
import com.ohho.valetparking.domains.member.dto.SignInRequest;
import com.ohho.valetparking.global.error.exception.TokenExpiredException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

@Service
@Slf4j
public class JWTProvider {

    // 나중에 키 감추기
    @Value("${secretKey}")
    private String SECREAT_KEY;
    @Value("${refreshtokentime}")
    private String REFRESHTOKENTIME;
    @Value("${accesstokentime}")
    private String ACCESSTOKENTIME;

    public String accessTokenCreate(String email){

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(email)
                .setHeader(createHeader())
                .setClaims(createClaims(email))
                .setExpiration(createExpireDate("access", Long.parseLong(ACCESSTOKENTIME)))
                .signWith( createSigningKey(),SignatureAlgorithm.HS256);

        return jwtBuilder.compact();
    }

    public String refreshTokenCreate(String email){

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(email)
                .setHeader(createHeader())
                .setClaims(createClaims(email))
                .setExpiration(createExpireDate("refresh",Long.parseLong(REFRESHTOKENTIME)))
                .signWith( createSigningKey(),SignatureAlgorithm.HS256);

        return jwtBuilder.compact();
    }

    public boolean isValid(String token){
        // 토큰 만료일자가 현재시간보다 이전이면 true이고 이후이면 false를 반환한다.
        return Timestamp.valueOf(LocalDateTime.now())
                        .before(getClaimsFormToken(token).getExpiration());
    }

    // 1. header 생성
    private HashMap<String,Object> createHeader(){
        HashMap<String,Object> header = new HashMap<>();

        header.put("typ","JWT");
        header.put("alg","HS256");
        header.put("regDate",System.currentTimeMillis());

        return header;
    }

    // 2. claim 생성
    private HashMap<String,Object> createClaims( String email){
        HashMap<String,Object> claim = new HashMap<>();

        claim.put("email",email);

        return claim;
    }

    // 3. 시크릿 키로 마지막 꼬리 부분 생성 키 만들기
    private Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECREAT_KEY);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private Date createExpireDate (String type, long time) {
        if(type.equals("refresh")){
            return Timestamp.valueOf(LocalDateTime.now().plusWeeks(time));
        }
        return Timestamp.valueOf(LocalDateTime.now().plusHours(time));
    }

    private Claims getClaimsFormToken(String token) {
        try{

            Claims claims = Jwts.parserBuilder()
                                .setSigningKey(DatatypeConverter.parseBase64Binary(SECREAT_KEY))
                                .build()
                                .parseClaimsJws(token)
                                .getBody();
            return claims;

        }catch (ExpiredJwtException e){
            throw new TokenExpiredException("토큰이 만료되었습니다.");
        }
    }
    public String getEmailInFromToken(String token) {
        return (String) getClaimsFormToken(token).get("email");
    }


}
