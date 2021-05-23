package com.baizhi.springboot_jsp_shiro.service;

import com.baizhi.springboot_jsp_shiro.entity.Perms;
import com.baizhi.springboot_jsp_shiro.entity.Role;
import com.baizhi.springboot_jsp_shiro.entity.User;

import java.util.List;

public interface UserService {
    //注册用户
    void register(User user) ;
    User findByUserName(String username) ;
    User findRolesByUserName(String username) ;
    //    根据角色id查询权限集合
    List<Perms> findPermsByRoleId(String id) ;
}
