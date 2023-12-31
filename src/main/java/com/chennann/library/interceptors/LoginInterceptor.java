package com.chennann.library.interceptors;

import com.chennann.library.anno.RequireRole;
import com.chennann.library.utils.JwtUtil;
import com.chennann.library.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");


        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }


        String redisToken;
        Map<String, Object> claims;
        try {

            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            redisToken = operations.get(token);

            if (redisToken == null) {
                throw new RuntimeException();
            }
            claims = JwtUtil.parseToken(token);


        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }

        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            RequireRole requireRole = method.getMethodAnnotation(RequireRole.class);

            if (requireRole != null) {
                // 获取当前用户的角色
                String currentUserRole = claims.get("role").toString();

                // 检查用户是否有所需的角色
                if (!requireRole.value().equals(currentUserRole)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                    return false;
                }
            }
        }

        ThreadLocalUtil.set(claims);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        ThreadLocalUtil.remove();
    }
}