package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.util.JwtUtil;
import com.itheima.util.Md5Util;
import com.itheima.util.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username ,@Pattern(regexp = "^\\S{5,16}$") String password){
//        //校验用户名
//        if(username == null){
//            return Result.error("用户名为空,不允许注册");
//        }
//        if (password == null){
//            return Result.error("密码为空，不允许注册");
//        }
////        5~16位非空字符
//        if(username.trim().isEmpty()){
//            return Result.error("密码为空，不允许注册");
//        }
//        if(password.trim().isEmpty()){
//            return Result.error("密码为空，不允许注册");
//        }
//        if(username.length() > 16 || username.length() < 5){
//            return Result.error("用户名不符合要求5~16位");
//        }
//        if(password.length() > 16 || password.length() < 5){
//            return Result.error("密码不符合要求5~16位");
//        }
        //查询用户名
        User u =  userService.findByUserName(username);
        if(u==null){
            //注册
            userService.register(username,password);
            return Result.success();
        }else{
            //
            return Result.error("用户名已被占用");
        }
    }
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username ,@Pattern(regexp = "^\\S{5,16}$") String password){
        //根据用户名查询用户判断用户是否存在
        User user = userService.findByUserName(username);
        if(user != null){
            //判断密码是否正确 注意是密文
            if(Md5Util.checkPassword(password,user.getPassword())){
                Map<String ,Object> claims = new HashMap<>();
                claims.put("username",user.getUsername());
                claims.put("id",user.getId());
                String token = JwtUtil.genToken(claims);
                ValueOperations<String,String> operations = redisTemplate.opsForValue();
                operations.set(String.valueOf(user.getId()),token,1, TimeUnit.DAYS);
                return Result.success(token,"登陆成功");
            }else {
                return Result.error("密码错误");
            }
        }else {
            return Result.error("用户名错误");
        }

    }
    @GetMapping("/userInfo")
    public Result<User> userInfo(){
//        Map<String, Object> claims = JwtUtil.parseToken(token);//通过ThreadLocal优化

        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        ThreadLocalUtil.remove();
        return Result.success(user);
    }
    @PutMapping("/update")//更新资源
    public Result<Object> updateByUserName(@RequestBody @Validated User user){
        boolean b = userService.updateByUserName(user);
        if(b){
            return Result.success(userService.findByUserName(user.getUsername()));
        }else{
            return Result.error("更新失败");
        }

    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatarByUserId(@RequestParam String avatarUrl){
        Map<String,Object> map = ThreadLocalUtil.get();
        ThreadLocalUtil.remove();
        Integer id = (Integer) map.get("id");
        boolean b = userService.updateAvatarByUserName(avatarUrl, id);
        if(b){
            return Result.success(userService.findByUserId(id));
        }else{
            return Result.error();
        }

    }
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> pw){
        Map<String,Object> map = ThreadLocalUtil.get();
        ThreadLocalUtil.remove();
        Integer id = (Integer) map.get("id");
        String new_pwd = pw.get("new_pwd");
        String old_pwd = pw.get("old_pwd");
        String re_pwd = pw.get("re_pwd");
        User user = userService.findByUserId(id);
        if(StringUtils.hasLength(new_pwd)&&StringUtils.hasLength(old_pwd)&&StringUtils.hasLength(re_pwd)){
            if(Md5Util.checkPassword(old_pwd,user.getPassword())){
                if(new_pwd.equals(re_pwd)){
                    boolean b = userService.updatePwd(new_pwd,id);
                    if(b){
                        ValueOperations<String, String> operations = redisTemplate.opsForValue();
                        operations.getOperations().delete(String.valueOf(id));
                        return Result.success();
                    }else{
                        return Result.error();
                    }
                }else{
                    return Result.error("新旧密码不同");
                }
            }else{
                return Result.error("旧密码错误");}
        }else{
            return Result.error("缺少必要参数");}
    }
}
