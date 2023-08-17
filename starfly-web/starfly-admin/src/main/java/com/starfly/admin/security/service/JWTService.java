package com.starfly.admin.security.service;

import com.starfly.admin.constant.Constants;
import com.starfly.admin.domain.entity.Users;
import com.starfly.admin.security.domain.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JWTService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.expireTime}")
    private int expireTime;

    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).
                compact();
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String setToken(){
        String uuid = UUID.randomUUID().toString();
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, uuid);
        return createToken(claims);
    }

    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = request.getHeader(header);
        try {
            Claims claims = parseToken(token);
            String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
            System.out.println(uuid);
            return new LoginUser(1,new Users());
        } catch (Exception e) {
            return null;
        }
    }

}
