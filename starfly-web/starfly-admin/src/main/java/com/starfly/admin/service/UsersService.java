package com.starfly.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starfly.admin.domain.entity.Users;
import com.starfly.admin.domain.qo.UserQO;

import java.util.List;

/**
* @author xiaoshanshan
* @description 针对表【users】的数据库操作Service
* @createDate 2023-07-23 15:12:35
*/
public interface UsersService extends IService<Users> {

    List<Users> AllUser();

    Boolean AddUser(UserQO userQO);

    Users selectUserByUserName(String userName);
}
