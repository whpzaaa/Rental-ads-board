package org.example.informationsystem.Config;

import org.example.informationsystem.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

//package org.example.informationsystem.Config;
//
//import org.example.i
//import org.example.informationsystem.Properties.JwtProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//public class SecurityConfig {
//
//    @Autowired
//    private JwtProperties jwtProperties;
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // 关闭 CSRF，因为是无状态REST API
//                .csrf(csrf -> csrf.disable())
//                // 设置无状态 session
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                // 配置 URL 访问权限 (示例配置，根据项目实际调整)
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/api/auth/**").permitAll()  // 登录、注册接口等公开
//                        .requestMatchers("/api/ads", "/api/ads/page/**").permitAll()  // 广告浏览接口公开
//                        .requestMatchers("/api/ads/create", "/api/ads/edit/**", "/api/ads/delete/**")
//                        .hasRole("OWNER")  // 只有房东角色才能管理广告，注意：角色名称前缀可能需要“ROLE_”
//                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                        .anyRequest().authenticated())
//                // 将自定义JWT过滤器加入到UsernamePasswordAuthenticationFilter之前
//                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                // 使用默认配置其它部分
//                .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }
//}
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // 启用 CORS，并使用默认配置
                .csrf(csrf -> csrf.disable()) // 采用 Lambda 表达式禁用 CSRF
                .authorizeHttpRequests(authorize -> authorize
                        // 用户登录、注册接口放行
                        .requestMatchers("/api/users/login", "/api/users/register").permitAll()
                        // 修改密码接口，登录后所有角色均可访问
                        .requestMatchers(POST, "/api/users/change-password").authenticated()

                        // 广告相关接口
                        // GET 查看广告，任何登录用户都可访问
                        .requestMatchers(GET, "/api/ads/**").authenticated()
                        // POST / PUT / DELETE 广告，仅房东与管理员可操作
                        .requestMatchers(POST, "/api/ads").hasAnyRole("LANDLORD", "ADMIN")
                        .requestMatchers(PUT, "/api/ads/**").hasAnyRole("LANDLORD", "ADMIN")
                        .requestMatchers(DELETE, "/api/ads/**").hasAnyRole("LANDLORD", "ADMIN")

                        // 图片相关接口
                        // GET 获取图片信息，登录用户均可访问
                        .requestMatchers(GET, "/api/images/**").authenticated()
                        // POST 上传图片、PUT 更新图片、DELETE 删除图片，仅房东与管理员可操作
                        .requestMatchers(POST, "/api/images/upload").hasAnyRole("LANDLORD", "ADMIN")
                        .requestMatchers(PUT, "/api/images/**").hasAnyRole("LANDLORD", "ADMIN")
                        .requestMatchers(DELETE, "/api/images/**").hasAnyRole("LANDLORD", "ADMIN")

                        // 用户管理接口（除登录、注册、修改密码外的所有 /api/users/** 接口）
                        // 只能被管理员访问：包含查询、修改、删除用户
                        .requestMatchers(GET, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(POST, "/api/users").hasRole("ADMIN")
                        .requestMatchers(PUT, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(DELETE, "/api/users/**").hasRole("ADMIN")

                        // 其他接口需要认证
                        .anyRequest().authenticated()
                );
        // 将自定义 JWT 过滤器添加到 Spring Security 的过滤链中，确保在 UsernamePasswordAuthenticationFilter 之前执行
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}