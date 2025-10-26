package com.itheima.service;

import com.itheima.pojo.User;

public interface UserService {
    //根据用户名查找用户
    User findByUserName(String username);
    //注册用户
    void register(String username, String password);

    boolean updateByUserName(User user);

    boolean updateAvatarByUserName(String imgurl , Integer id);
    User findByUserId(Integer id);

    boolean updatePwd(String newPwd, Integer id);
}
