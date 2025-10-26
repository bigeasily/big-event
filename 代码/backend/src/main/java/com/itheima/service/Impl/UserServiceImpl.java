package com.itheima.service.Impl;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void register(String username, String password) {
        //加密
        String md5String = Md5Util.getMD5String(password);
        //添加至数据库
        userMapper.add(username,md5String);
    }

    @Override
    public boolean updateByUserName(User user) {
        return userMapper.updateByUserName(user);
    }

    @Override
    public boolean updateAvatarByUserName(String imgurl, Integer id) {
        return userMapper.updateAvatarByUserName(imgurl,id);
    }

    @Override
    public User findByUserId(Integer id) {
        return userMapper.findByUserId(id);
    }

    @Override
    public boolean updatePwd(String newPwd, Integer id) {
        return userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
    }
}
