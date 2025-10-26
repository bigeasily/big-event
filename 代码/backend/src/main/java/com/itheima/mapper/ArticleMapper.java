package com.itheima.mapper;

import com.itheima.pojo.Article;
import org.apache.ibatis.annotations.*;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Select("select big_event.article.id from big_event.article where create_user = #{CreateUser}")
    List<Integer> findAllIdByCreateUser(Integer CreateUser);

    @Insert("insert into big_event.article(title,content,cover_img,state,category_id,create_user,update_time,create_time)values (#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},now(),now())")
    boolean addArticle(Article article);

    List<Article> list(Integer userId, Integer categoryId, String state);

    @Update("update big_event.article set title = #{title},content = #{content},cover_img = #{coverImg},state = #{state},category_id = #{categoryId} where id=#{id}")
    boolean updateById(Article article);
    @Select("select * from big_event.article where id = #{id}")
    Article getArticleDetailById(Integer id);
    @Delete("delete from big_event.article where id = #{id}")
    boolean deleteArticleById(Integer id);
}
