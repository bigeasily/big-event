package com.itheima.pojo;

import com.itheima.anno.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @NotNull(groups = update.class)
    private Integer id;//主键ID
    @Pattern(regexp = "^[^\\r\\n]{1,10}$")
    private String title;//文章标题
    @NotEmpty
    private String content;//文章内容
    @NotNull
    @URL(message = "封面图像URL格式无效")
    private String coverImg;//封面图像
//    @Pattern(regexp = "^(已发布|草稿)$")
    @State
    private String state;//发布状态 已发布|草稿
    @NotNull
    private Integer categoryId;//文章分类id
    private Integer createUser;//创建人ID
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间

    public interface update{}
}