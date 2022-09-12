package com.ohho.valetparking.global.security;


import com.ohho.valetparking.domains.member.entity.SignIn;
import com.ohho.valetparking.domains.member.dto.SignInRequest;
import com.ohho.valetparking.global.error.exception.TokenExpiredException;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

public class JWTProvider {

    // 나중에 키 감추기
    private static final String SECREAT_KEY = "thisiskeyforeasyvaletparkingservicemadebylogan";
    private static final int ONEWEEK = 1;
    private static final int ONEHOUR = 1;

    public static String accessTokenCreate(SignIn sign){

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(sign.getEmail())
                .setHeader(createHeader())
                .setClaims(createClaims(sign))
                .setExpiration(createExpireDate("access", ONEHOUR))
                .signWith( createSigningKey(),SignatureAlgorithm.HS256);

        return jwtBuilder.compact();
    }

    public static String refreshTokenCreate(SignIn sign){

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(sign.getEmail())
                .setHeader(createHeader())
                .setClaims(createClaims(sign))
                .setExpiration(createExpireDate("refresh",ONEWEEK))
                .signWith( createSigningKey(),SignatureAlgorithm.HS256);

        return jwtBuilder.compact();
    }

    public static boolean isValid(String token){
        // 토큰 만료일자가 현재시간보다 이전이면 true이고 이후이면 false를 반환한다.
        return Timestamp.valueOf(LocalDateTime.now())
                        .before(getClaimsFormToken(token).getExpiration());
    }

    // 1. header 생성
    private static HashMap<String,Object> createHeader(){
        HashMap<String,Object> header = new HashMap<>();

        header.put("typ","JWT");
        header.put("alg","HS256");
        header.put("regDate",System.currentTimeMillis());

        return header;
    }

    // 2. claim 생성
    private static HashMap<String,Object> createClaims( SignIn sign){
        HashMap<String,Object> claim = new HashMap<>();

        claim.put("email",sign.getEmail());
        claim.put("role",sign.getDepartment());
        return claim;
    }

    // 3. 시크릿 키로 마지막 꼬리 부분 생성 키 만들기
    private static Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECREAT_KEY);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private static Date createExpireDate (String type,int time) {
        if(type.equals("refresh")){
            return Timestamp.valueOf(LocalDateTime.now().plusWeeks(time));
        }
        return Timestamp.valueOf(LocalDateTime.now().plusHours(time));
    }

    private static Claims getClaimsFormToken(String token) {
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

    public static String getEmailInFromToken(String token) {
        return (String) getClaimsFormToken(token).get("email");
    }

    public static int getDepartmentFromToken(String token) {
        return  (int)getClaimsFormToken(token).get("department");
    }

    public static SignIn getSignInFromToken(String token) {
        return SignIn.builder(new SignInRequest(getEmailInFromToken(token)
                                                ,"test"
                                            ,getDepartmentFromToken(token)))
                     .build();
    }

}
