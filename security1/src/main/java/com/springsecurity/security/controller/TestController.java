package com.springsecurity.security.controller;

import com.springsecurity.security.entity.Users;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/hello")
    public String hello(){
        return "hello security";
    }
    @GetMapping("/index")
    public String index(){
        return "hello index";
    }

    @GetMapping("/update")
    //@Secured({"ROLE_sale","ROLE_admin"})
    //@PreAuthorize("hasAnyAuthority('admins')")
    @PostAuthorize("hasAnyAuthority('admin')")
    public String update(){
        System.out.println("没有权限");
        return "hello update";
    }
    @GetMapping("/getall")
    @PostAuthorize("hasAnyAuthority('admins')")
    @PostFilter("filterObject.name=='admin6'")
    public List<Users> getAllUser(){
        ArrayList<Users> list=new ArrayList<>();
        list.add(new Users(6,"admin7","654321"));
        list.add(new Users(7,"admin6","123456"));
        System.out.println(list);
        return list;
    }

}
