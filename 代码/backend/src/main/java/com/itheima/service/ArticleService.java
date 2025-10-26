package com.itheima.service;

import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;

import java.util.List;

public interface ArticleService {

    Object addArticle(Article article);

    //判断该id是否为登陆用户的
    boolean isID(Integer id);
    // 查找该用户的所有文章的id
    List<Integer> findAllIdByCreateUser();
    //条件列表分页查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
    //根据文章id更新文章内容
    Object updateById(Article article);
    //
    Object getArticleDetailById(Integer id);

    Object deleteArticleById(Integer id);
}
