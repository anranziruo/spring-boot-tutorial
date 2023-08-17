package com.starfly.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starfly.admin.domain.entity.Users;
import com.starfly.admin.domain.qo.UserQO;
import com.starfly.admin.mapper.UsersMapper;
import com.starfly.admin.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author xiaoshanshan
* @description 针对表【users】的数据库操作Service实现
* @createDate 2023-07-23 15:12:35
*/
@Service
@RequiredArgsConstructor
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService {

    private final UsersMapper usersMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<Users> AllUser(){
        redisTemplate.opsForValue().set("cr7", "goat");
        String value = redisTemplate.opsForValue().get("cr7");
        System.out.println(value);
        return usersMapper.all();
    }

    @Override
    public Boolean AddUser(UserQO userQO) {
        return false;
    }

    @Override
    public Users selectUserByUserName(String userName) {
        return usersMapper.selectUserByUserName(userName);
    }
}




