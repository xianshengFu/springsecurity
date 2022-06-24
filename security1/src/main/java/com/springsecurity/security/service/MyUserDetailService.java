package com.springsecurity.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springsecurity.security.entity.Users;
import com.springsecurity.security.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserDetailsService")
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsersMapper usersMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用usersmapper方法查询数据库
        QueryWrapper<Users> wrapper=new QueryWrapper<>();
        wrapper.eq("name",username);
        Users users=usersMapper.selectOne(wrapper);
        //判断
        if (users==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<GrantedAuthority> auths= AuthorityUtils.commaSeparatedStringToAuthorityList("admins,ROLE_sale");
        String password=passwordEncoder.encode(users.getPwd());
        return new User(users.getName(),password,auths);
    }
}
