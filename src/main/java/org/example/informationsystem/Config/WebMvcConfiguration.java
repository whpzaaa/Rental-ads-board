//package org.example.informationsystem.Config;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.example.informationsystem.interceptor.JwtTokenAdminInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//import java.util.List;
//
///**
// * 配置类，注册web层相关组件
// */
//@Configuration
//@Slf4j
//public class WebMvcConfiguration extends WebMvcConfigurationSupport {
//
//    @Autowired
//    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;
//
//    /**
//     * 注册自定义拦截器
//     *
//     * @param registry
//     */
//    protected void addInterceptors(InterceptorRegistry registry) {
//        log.info("开始注册自定义拦截器...");
//        registry.addInterceptor(jwtTokenAdminInterceptor)
//                .addPathPatterns("/api/**")
//                .excludePathPatterns("/api/users/login")
//                .excludePathPatterns("/api/users/register");
//    }
//}
