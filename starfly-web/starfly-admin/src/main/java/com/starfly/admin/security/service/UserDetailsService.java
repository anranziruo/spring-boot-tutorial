package com.starfly.admin.security.service;

import com.starfly.admin.domain.entity.Users;
import com.starfly.admin.security.domain.LoginUser;
import com.starfly.admin.service.UsersService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {


    @Resource
    private UsersService usersService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersService.selectUserByUserName(username);
        if (Objects.isNull(users)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new LoginUser(users.getId(),users);
    }
}
