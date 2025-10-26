package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper {
    @Select("select * from big_event.user where username = #{username}")
    User findByUserName(String username);

    @Insert("insert into big_event.user (username,password,create_time,update_time) values(#{username},#{password},now(),now())")
    void add(String username, String password);

    @Update("update big_event.user set nickname = #{nickname},email = #{email},update_time = now() where username = #{username}")
    boolean updateByUserName(User user);

    @Update("update big_event.user set user_pic = #{imgurl},update_time = now() where id = #{id}")
    boolean updateAvatarByUserName(String imgurl, Integer id);

    @Select("select * from big_event.user where id = #{id}")
    User findByUserId(Integer id);

    @Update("UPDATE big_event.user set password = #{newPwd} where id = #{id}")
    boolean updatePwd(String newPwd,  Integer id);
}
