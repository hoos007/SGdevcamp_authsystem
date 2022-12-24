package com.smile.AuthServer.util;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {

	private final String key = "f8d02c35b67190af8e6f826e6d7d58576ef262bdb09571c2ebc8a9dfab07419ab36429618b110b0c2e3e92cdfca0444208020b8596203e1b89edc3089de626f6";
	// 터미널에서 'openssl rand -hex 64' 로 생성한 랜덤값 사용함
	   
	//jwt 토큰 생성
	public String createJwt(String param1, int param2) {//payload에 넣을 파라미터
    	//자신이 넣고자 하는 파라미터의 수에 따라 payload의 값은 변경된다.
		LocalDateTime now = LocalDateTime.now();

        //Header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT"); 
        headers.put("alg", "HS256");
		//헤더에는 jwt의 암호화 방법 정보가 들어있다.

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("id", param1);
        payloads.put("permission", param2);
        //실제적인 jwt의 데이터를 담당하는 부분이다.
        
        // 토큰 Builder
        String jwt = Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads) // Claims 설정
                .setExpiration(java.sql.Timestamp.valueOf(now.plusMinutes(20)))
                .signWith(SignatureAlgorithm.HS256, key.getBytes()) // HS256과 Key로 Sign
                .compact(); // 토큰 생성

        return jwt;
    }

    //jwt 토큰 검증
    public Map<String, Object> checkJwt(String jwt) throws UnsupportedEncodingException {
        Map<String, Object> claimMap = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key.getBytes("UTF-8")) // 키 설정
                    .parseClaimsJws(jwt) // jwt의 정보를 파싱해서 시그니처 값을 검증한다.
                    .getBody();

            claimMap = claims;
            
        } catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우
            System.out.println(e);
            
        } catch (Exception e) { // 나머지 에러의 경우
            System.out.println(e);
        }
        return claimMap;
    }    
}