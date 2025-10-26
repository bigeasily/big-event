package com.itheima.controller;

import com.itheima.pojo.Category;
import com.itheima.pojo.Result;
import com.itheima.service.CategoryService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryContorller {

    private CategoryService categoryService;
    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Result add(@RequestBody @Validated Category category){
        categoryService.add(category);
        return Result.success();
    }
    @GetMapping
    public Result<List<Category>> getAllCategory(){
        return Result.success(categoryService.getAllCategory()) ;
    }
    @GetMapping("/detail")
    public Result<Object> getCategoryById(@RequestParam Integer id){
        Category categoryById = categoryService.getCategoryById(id);
        if (categoryById.getId() == -1){
            return Result.error("该用户不存在这个文章分类id");
        }
        else{
            return Result.success(categoryById);
        }
    }
    @PutMapping
    public Result<Object> updateCategoryById(@RequestBody @Validated(Category.Update.class) Category category) {
        Object res = categoryService.updateCategoryById(category);

        if (res == null) {
            return Result.success("更新成功");
        } else if (res.equals(-1)) {
            return Result.error("该用户不存在这个文章分类id");
        } else {
            return Result.error("参数不合法");
        }
    }
    @DeleteMapping
    public Result<Object> deleteCategoryById(@RequestParam Integer id){
        Object res =  categoryService.deleteCategoryById(id);
        if(res.equals(true)){
            return Result.success("删除成功");
        }else if(res.equals(-1)){
            return Result.error("该用户不存在这个文章分类id");
        }else{
            return Result.error("删除失败");
        }
    }


}
