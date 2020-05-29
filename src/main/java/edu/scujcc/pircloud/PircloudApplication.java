package edu.scujcc.pircloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author FSMG
 */
@SpringBootApplication
public class PircloudApplication implements WebMvcConfigurer {
    @Autowired
    AuthInterceptor authInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(PircloudApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor);
    }


}
