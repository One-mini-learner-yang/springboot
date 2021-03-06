package com.yang.springboot.config;

import com.yang.springboot.Filter.Myfilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Configuration
public class mvcConfiguration extends WebMvcConfigurationSupport {
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean()
//    {
//        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(new Myfilter());
//        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*","/jsp/login.jsp"));
//        return filterRegistrationBean;
//    }
    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
            registry.addRedirectViewController("/","jsp/login.jsp");
    }

//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        super.addResourceHandlers(registry);
//    }

    @Bean
    public LocaleResolver localeResolver(){
        return new loacleResolver();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor(){
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                System.out.println(request.getRequestURI());
                if(request.getRequestURI().equals("/controller/hello"))
                {
                    System.out.println("拦截器生效");
                }
                return true;
            }
            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                if(request.getRequestURI().equals("/controller/hello"))
                {
                    System.out.println("拦截后面的");
                }
            }
        }).addPathPatterns().excludePathPatterns();//配置拦截路径和不拦截路径
    }
}
