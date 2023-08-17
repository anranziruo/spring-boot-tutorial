package com.starfly.admin.security.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JWTService jwtService;


    // 用户验证


    public String login(String username, String password) {
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadCredentialsException("用户不存在/密码错误");
        }
        // 生成token
        return jwtService.setToken();
    }
}
