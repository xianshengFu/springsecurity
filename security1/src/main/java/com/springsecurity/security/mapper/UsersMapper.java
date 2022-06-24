package com.springsecurity.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springsecurity.security.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

}
