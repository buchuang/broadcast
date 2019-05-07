package com.vr.plateserver;

import com.vr.plateserver.filter.LoginInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@SpringBootApplication(scanBasePackages = {"com.vr"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.vr.userserviceapi.webClientInterface")
@MapperScan(basePackages = "com.vr.plateserver.dao")
public class PlateServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlateServerApplication.class, args);
    }
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean(LoginFilter loginFilter){
//        FilterRegistrationBean filterRegist=new FilterRegistrationBean();
//        filterRegist.setFilter(loginFilter);
//        List<String> urlpatterns=new ArrayList<>();
//        urlpatterns.add("/*");
//        filterRegist.setUrlPatterns(urlpatterns);
//        return filterRegist;
//    }
//    @Bean
//    public InterceptorRegistry interceptorRegistry(LoginInterceptor interceptor){
//        InterceptorRegistry registry=new InterceptorRegistry();
//        registry.addInterceptor(interceptor);
//        return registry;
//    }
}
