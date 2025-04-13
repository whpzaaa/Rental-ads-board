package org.example.informationsystem.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.informationsystem.Properties.JwtProperties;
import org.example.informationsystem.context.BaseContext;
import org.example.informationsystem.utills.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(urlPatterns = "/*")
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtProperties jwtProperties;  // 包含 token 名称和秘钥等配置

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestURL = request.getRequestURL().toString();
        // 对于 login 和 register 等公开接口，直接放行
        if (requestURL.contains("login") || requestURL.contains("register")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 从请求头中获取 token
        String header = request.getHeader(jwtProperties.getAdminTokenName());

        // 如果 token 不存在或格式不符合要求，直接返回 401
        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            logger.warn("请求缺失或格式错误的token, 请求地址: {}", request.getRequestURL());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // 如果携带 token，则处理解析
        String token = header.substring(7); // 去掉 "Bearer " 前缀
        try {
            logger.info("JWT校验: {}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long userId = Long.valueOf(claims.get("userId").toString());
            BaseContext.setCurrentId(userId);
            logger.info("当前用户ID: {}", userId);

            // 从claims中获取角色信息(假设字段为 "userRole")
            String role = claims.get("userRole").toString();
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            // 构造角色信息（注意 Spring Security 中通常要求角色前缀 "ROLE_"）
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));

            // 设置当前用户的认证信息到 SecurityContext
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            logger.info("用户角色为: {}", role);
        } catch (Exception ex) {
            logger.error("JWT解析或验证失败: {}", ex.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // 继续过滤链
        filterChain.doFilter(request, response);
    }
}