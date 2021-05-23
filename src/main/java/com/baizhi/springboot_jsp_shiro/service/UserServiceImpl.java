package com.baizhi.springboot_jsp_shiro.service;

import com.baizhi.springboot_jsp_shiro.dao.UserDAO;
import com.baizhi.springboot_jsp_shiro.entity.Perms;
import com.baizhi.springboot_jsp_shiro.entity.User;
import com.baizhi.springboot_jsp_shiro.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO ;
    @Override
    public void register(User user) {
        //处理业务调用dao
        //生成随机盐
        String salt = SaltUtils.getSalt(8);
        user.setSalt(salt) ;//保存随机盐
        //明文密码进行md5+salt+hash散列
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt,1024) ;
        user.setPassword(md5Hash.toHex());
        userDAO.save(user);
    }

    @Override
    public User findByUserName(String username) {
        return userDAO.findByUserName(username);
    }

    @Override
    public User findRolesByUserName(String username) {
        return userDAO.findRolesByUserName(username);
    }

    @Override
    public List<Perms> findPermsByRoleId(String id) {
        return userDAO.findPermsByRoleId(id);
    }
}
