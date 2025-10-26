package com.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.ArticleMapper;
import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;
import com.itheima.service.ArticleService;
import com.itheima.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleMapper articleMapper;
    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }


    @Override
    public Object addArticle(Article article) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Object userCreate = map.get("id");
        article.setCreateUser((Integer) userCreate);
        return articleMapper.addArticle(article);
    }

    @Override
    public boolean isID(Integer id) {
        Map<String,Object> map = ThreadLocalUtil.get();
        //需要查找该所有文章的id
        return false;
    }

    @Override
    public List<Integer> findAllIdByCreateUser() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        return articleMapper.findAllIdByCreateUser(id);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //创建PageBean对象
        PageBean<Article> pb = new PageBean<>();
        //开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);
        //调用Mapper
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer createUser = (Integer) map.get("id");
        List<Article> as =  articleMapper.list(createUser,categoryId,state);
        //Page中提供了方法，可以获取PageHelper分页查询后得到的总记录条数和当前页数据;
        Page<Article> p = (Page<Article>) as;
        pb.setTotal(p.getTotal());//获取总条数;
        pb.setItems(p.getResult());//获取当页数据;
        return pb;
    }

    //判断该用户是否有权限操作该id数据
    /**】
     *
     * @param id 该id为文章的主键
     * @return
     */
    public boolean isId(Integer id){
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userid = (Integer) map.get("id");
        List<Integer> allIdByCreateUser = articleMapper.findAllIdByCreateUser(userid);
        for (Integer i : allIdByCreateUser) {
            if(Objects.equals(i, id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Object updateById(Article article) {
        boolean b = isId(article.getId());
        if(b){
            return articleMapper.updateById(article);
        }else{
            return -1;
        }


    }

    @Override
    public Object getArticleDetailById(Integer id) {
        boolean b = isId(id);
        if(b){
            return articleMapper.getArticleDetailById(id);
        }else{
            return -1;
        }

    }

    @Override
    public Object deleteArticleById(Integer id) {
        boolean b = isId(id);
        if(b){
            return articleMapper.deleteArticleById(id);
        }else{
            return -1;
        }
    }
}
