package org.example.microsvc.mobileapp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@Slf4j
public class JwtCodeDecodetest {
    @Test
    public void testEncodeDecode(){
        String userId = "7df11015-c2ab-40a4-b0a9-d06cb759a024";
        String key = "abcdefgh";
        String token = Jwts.builder().setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis()+ Long.parseLong("8460000")
                )).signWith(SignatureAlgorithm.HS512, key)
                .compact();

        //token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlNjBlNmMzNy04M2I4LTQzZmItOWMyNi04ZTI0ZDBlYTc3MzMiLCJleHAiOjE1OTY0ODQyMTF9.scr0DzE5K0gGuJA8sdtICuOjUll_z4bv0ou5NiafdO7n3XgbN0sKFmlAeqnA_s2iyeqOyOr92vLzQ_pTX7pmjw";
        //String prefix = "Bearer ";
        System.out.println("token: " + token);
        log.info("userId: ", userId);
        log.info("token: ", token);
        JwtParser parser = Jwts.parser().setSigningKey(key);
        Claims body = parser.parseClaimsJws(token).getBody();
        String userIdDecoded = body.getSubject();
        log.info("userIdDecoded: ", userIdDecoded);
        Assertions.assertEquals(userId, userIdDecoded);
    }
}
