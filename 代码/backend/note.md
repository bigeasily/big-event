

1，通过jwt令牌修改，不登陆不开放权限

logincontorller创建jwt令牌 --> Restul返回前端请求头 --> 请求头携带，拦截器request 获取头进行校验决定放行还是不放行          bingo

2，添加 新增文章分类校验是否已经存在

​      添加 更新文章分类校验是否已经存在

3, 添加文章分类不能添加到别人创建的分类id里面

接口 实例对象

接口中没有声明的方法无法调用

jwt令牌

@RequestHeader(name = "") 请求头名称

```java
public Result<Object> userinfo(@RequestHeader(name = "Authorization")String token)
```

`Authorization在哪设置的？`

`通过JwtUtil 生成在controller返回`

`拦截器`

`HandlerInterceptor`

`WebMvcConfigurer`

```java
@JsonIgnore //隐藏属性json，比如密码不发送给result
```

```xml
mybatis:
  configuration:
    map-underscore-to-camel-case: true
开启mybatis驼峰命名，实体类必须全为驼峰命名，数据库字段为_分格
```

ThreadLocal 提供线程局部变量

线程安全，要remove

`@RequestBody User user`

作用：把 **请求体（前端请求）里的 JSON** 自动反序列化成一个 `User` 对象。

`@Update("update big_event.user set user_pic = #{user.userPic}, update_time = now() where id = #{user.id}")`
`boolean updateAvatarByUserName(@Param("user") User user);`

@Param("user")自定义对象名

```java
@RequestParam //根据前端的key值
```

##### 发送json自定义日期格式

实体类字段上添加@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")

##### 分组校验

定义分组

定义校验项时指定归属的分组

校验时指定要校验的分组

```java
public interface Add extends Default {};
public interface Update extends Default {};//继承未指定分组的
    @NotNull(groups = Update.class)//指定分组
    private Integer id;//主键ID
```

要在使用的时候添加，不添加为默认

```java
public Result<Object> updateCategoryById(@RequestBody @Validated(Update.class) Category category){}
```

```java
@NotNull → **非 null**（宽松，不管空字符串/空集合
@NotEmpty → **非 null + 非空**（比 @NotNull 更严格）```
@Nonnull → **声明**（给人/工具看的，不会真正校验）
```

##### 发布状态 已发布|草稿，只有这两种状态匹配正则

^(已发布|草稿)$

##### 自定义注解

1，自定义注解State

![image-20250927180923214](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20250927180923214.png)

2, 自定义校验数据的类StateValidation实现ConstraintValidator接口

![image-20250927180706608](D:\新桌面\练习\学习\java\SpringBoot\novel-manager\notemd_img\image-20250927180706608.png)

3,在需要校验的地方使用自定义注解

![image-20250927180556457](D:\新桌面\练习\学习\java\SpringBoot\novel-manager\notemd_img\image-20250927180556457.png)

##### 动态sql

```xml
<!--动态sql-->
<select id="list" resultType="com.itheima.pojo.Article">
    select * from article
    <where>
        <if test="categoryId!=null">
            category_id=#{categoryId}
        </if>
        <if test="state!=null">
            and state=#{state}
        </if>
        and create_user=#{userId}
    </where>
</select>
```

```java
d//创建PageBean对象
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
```

mybatis sql语句返回值不写Object读不出或读不完整数据

文件上传



##### redis令牌校验

在修改密码修改后使原有的令牌失效

```xml
<!--        redis坐标-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
```

```yml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      password: root@123456
```

```java
ValueOperations<String, String> operations = redisTemplate.opsForValue();
operations.getOperations().delete((String) map.get("id"));//删除Redis中的数据，根据键
```

添加redis令牌校验的键为用户id时，要比较当前的redis中的token是否和传入的一样，旧的token会被覆盖

而以token为键时，只需考虑该键是否为null就可以了,可以允许用户在不同设备同时登录，因为旧的token不会被删除

```java
if(redisToken == null || !redisToken.equals(token)){
    throw new RuntimeException();
}
```

springboot 部署 package打包

java -jar XXX.jar 启动

命令行参数

环境变量

同级目录下application.yml配置文件

项目目录下的application.yml配置文件

优先级自上向下逐级降低

