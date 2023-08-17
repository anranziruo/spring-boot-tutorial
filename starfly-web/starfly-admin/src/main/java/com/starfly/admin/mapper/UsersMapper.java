package com.starfly.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starfly.admin.domain.entity.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author xiaoshanshan
* @description 针对表【users】的数据库操作Mapper
* @createDate 2023-07-23 15:12:35
* @Entity generator.entity.Users
*/
@Mapper
public interface UsersMapper extends BaseMapper<Users> {
    List<Users> all();

    Users selectUserByUserName(String userName);
}




