package com.itheima.service.Impl;

import com.itheima.mapper.CategoryMapper;
import com.itheima.pojo.Category;
import com.itheima.pojo.Result;
import com.itheima.service.CategoryService;
import com.itheima.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryMapper categoryMapper;
    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void add(Category category) {
        Map<String,Object> map = ThreadLocalUtil.get();
        category.setCreateUser((Integer) map.get("id"));
        categoryMapper.add(category);
    }

    @Override
    public List<Category> getAllCategory() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer CreateUser = (Integer) map.get("id");
        return categoryMapper.getAllCategory(CreateUser);
    }



    @Override
    public Category getCategoryById(Integer id) {
        boolean flag = isId(id);
        if(flag){
            return categoryMapper.getCategoryById(id);
        }else {
            Category category1 = new Category();
            category1.setId(-1);
            return category1;
        }
    }

    @Override
    public boolean isId(Integer id) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer CreateUser = (Integer) map.get("id");
        List<Category> categoryList = categoryMapper.getAllCategory(CreateUser);
        for (Category category : categoryList) {
            if(Objects.equals(category.getId(), id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Object updateCategoryById(Category category) {
        if(!StringUtils.hasLength(String.valueOf(category.getId()))|| !StringUtils.hasLength(category.getCategoryName()) ||!StringUtils.hasLength(category.getCategoryAlias()) ){
            return -2;//传递了不合法的参数
        }
        boolean flag = isId(category.getId());
        if(flag){
            return categoryMapper.updateCategoryById(category);
        }else{
            return -1;//不存在该id
        }
    }

    @Override
    public Object deleteCategoryById(Integer id) {
        boolean flag = isId(id);
        if(flag){
             return categoryMapper.deleteCategoryById(id);
        }else{
            return -1;
        }
    }
}
