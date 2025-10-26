package com.itheima.service;

import com.itheima.pojo.Category;

import java.util.List;

public interface CategoryService {
    //新增分类
    void add(Category category);
    //获取该用户所有的文章分类
    List<Category> getAllCategory();

    Category getCategoryById(Integer id);

    boolean isId(Integer id);

    Object updateCategoryById(Category category);

    Object deleteCategoryById(Integer id);
}
