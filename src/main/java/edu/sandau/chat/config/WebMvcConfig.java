package edu.sandau.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import static org.springframework.web.cors.CorsConfiguration.ALL;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    /***
     * 允许接受CORS的跨域请求
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 限制了路径和域名的访问
        /*registry.addMapping("/api*").allowedOrigins("http://localhost:8081");*/
        registry.addMapping("/**")
                .allowedOrigins(ALL)
                .allowedMethods(ALL)
                .allowedHeaders(ALL)
                .allowCredentials(true);
    }

}
