package com.itheima.interceptors;

import com.itheima.util.JwtUtil;
import com.itheima.util.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token = request.getHeader("Authorization");

        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            Map<String, Object> claims = JwtUtil.parseToken(token);
            String redisToken = operations.get(String.valueOf(claims.get("id")));//获取到的ID是Integer，存入的是String
            if(redisToken == null || !redisToken.equals(token)){
                throw new RuntimeException();
            }
            ThreadLocalUtil.set(claims);
            //放行
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }
}
