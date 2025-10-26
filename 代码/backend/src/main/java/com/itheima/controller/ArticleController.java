package com.itheima.controller;

import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private ArticleService articleService;
    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }
    @PostMapping
    public Result<Object> addNew(@RequestBody @Validated Article article){
        Object o = articleService.addArticle(article);
        if((boolean)o){
            return Result.success();
        }else{
            return Result.error();
        }
    }
    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum ,Integer pageSize,@RequestParam(required = false) Integer categoryId,@RequestParam(required = false) String state){
        PageBean<Article> o = articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(o);
    }
    @PutMapping()
    public Result<Object> updateById(@RequestBody @Validated(Article.update.class) Article article){
        Object o = articleService.updateById(article);
        if(o == null){
            return Result.error();
        }
        if(o instanceof Integer){
            if((Integer) o == -1){
                return Result.error("没有此文章");//传入文章主键id
            }else {
                return Result.error();
            }
        } else{
            return Result.success(o);
        }
    }
    @GetMapping("/detail")
    public Result<Object> getArticleDetailById(@RequestParam Integer id){
        Object o = articleService.getArticleDetailById(id);
        if(o == null){
            return Result.error();
        }
        if(o instanceof Integer){
            return Result.error("你没有该文章");
        }
        return Result.success(o);
    }
    @DeleteMapping
    public Result<Object> deleteArticleById(@RequestParam Integer id){
        Object o = articleService.deleteArticleById(id);
        if(o == null){
            return Result.error();
        }
        if(o instanceof Integer){
            return Result.error("你没有该文章");
        }
        return Result.success(o);
    }

}
