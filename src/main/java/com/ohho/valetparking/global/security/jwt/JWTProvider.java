package com.ohho.valetparking.global.security.jwt;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohho.valetparking.global.error.exception.TokenExpiredException;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

@Component
@Slf4j
@Getter
public class JWTProvider {

    // 나중에 키 감추기
    @Value("${secretKey}")
    private String SECREAT_KEY;
    @Value("${refreshtokentime:1}")
    private String REFRESHTOKENTIME;
    @Value("${accesstokentime:1}")
    private String ACCESSTOKENTIME;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String accessTokenCreate(TokenIngredient tokenIngredient){

        return Jwts.builder()
                   .setSubject(tokenIngredient.getEmail())
                   .setHeader(createHeader())
                   //.setClaims(createClaims(tokenIngredient))
                   .setExpiration(createExpireDate("access", Long.parseLong(ACCESSTOKENTIME)))
                   .signWith( createSigningKey(), SignatureAlgorithm.HS256 )
                   .claim("tokenIngredient",tokenIngredient)
                   .compact();
    }

    public String refreshTokenCreate(TokenIngredient tokenIngredient){
        return Jwts.builder()
                    .setSubject(tokenIngredient.getEmail())
                    .setHeader(createHeader())
                    //.setClaims(createClaims(tokenIngredient))
                    .setExpiration(createExpireDate("refresh",Long.parseLong(REFRESHTOKENTIME)))
                    .signWith( createSigningKey(),SignatureAlgorithm.HS256)
                    .claim("tokenIngredient",tokenIngredient)
                    .compact();

    }

    public boolean isValid(String token){
        Date tokenValidTime = getClaimsFormToken(token).getExpiration();
        return Timestamp.valueOf(LocalDateTime.now()).before(tokenValidTime);
    }

    // 1. header 생성
    private HashMap<String,Object> createHeader(){
        HashMap<String,Object> header = new HashMap<>();

        header.put("typ","JWT");
        header.put("alg","HS256");
        header.put("regDate",System.currentTimeMillis());

        return header;
    }


    /**
     * HashMap을 통해 만들 수도 있지만 Claim을 직접 설정할 수 있어서 그렇게 만들었다.
     * @param tokenIngredient
     * @auth Atom
     * @return
     */
    private HashMap<String,Object> createClaims( TokenIngredient tokenIngredient){
        HashMap<String,Object> claim = new HashMap<>();

        claim.put("email",tokenIngredient.getEmail());
        claim.put("department",tokenIngredient.getDepartment());
        claim.put("tokenIngredient",tokenIngredient);

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

        }catch (Exception e){
            throw new TokenExpiredException("토큰이 만료되었습니다.");
        }
    }

    public TokenIngredient getTokenIngredientFromToken(String token) throws JsonProcessingException {
        try{
            String tmp =  objectMapper.writeValueAsString(getClaimsFormToken(token).get("tokenIngredient"));
            return  objectMapper.readValue( tmp, TokenIngredient.class );
        }catch (JsonProcessingException e){
            throw new IllegalArgumentException("JsonProcessingException ::: 잘못된 토큰 값입니다.");
        }
    }

    @Deprecated
    public String getEmailInFromToken(String token) {
        return (String) getClaimsFormToken(token).get("email");
    }
    @Deprecated
    public int getDepartmentInFromToken(String token) {
        return  (int)getClaimsFormToken(token).get("department");
    }


}
