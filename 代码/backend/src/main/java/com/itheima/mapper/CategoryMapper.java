package com.itheima.mapper;

import com.itheima.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Insert("insert into big_event.category(category_name, category_alias, create_user, create_time, update_time) values (#{categoryName},#{categoryAlias},#{createUser},now(),now())")
    void add(Category category);

    @Select("select * from big_event.category where create_user = #{createUser}")
    List<Category> getAllCategory(Integer createUser);

    @Select("select * from big_event.category where id = #{id}")
    Category getCategoryById(Integer id);

    @Select("update big_event.category set category_name = #{categoryName},category_alias = #{categoryAlias} where id = #{id}")
    Object updateCategoryById(Category category);

    @Delete("delete from big_event.category where id = #{id};")
    boolean deleteCategoryById(Integer id);
}
