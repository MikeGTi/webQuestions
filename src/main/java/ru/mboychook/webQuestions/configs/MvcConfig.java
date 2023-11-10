package ru.mboychook.webQuestions.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/").setViewName("/login");
        registry.addViewController("/login").setViewName("/login");
        registry.addViewController("/logout").setViewName("/login");
        registry.addViewController("/error").setViewName("/error");

        registry.addViewController("/registration").setViewName("/registration");
        registry.addViewController("/users").setViewName("/users/index");
        registry.addViewController("/about").setViewName("/about");

        registry.addViewController("/assessments").setViewName("/assessments/index");
        registry.addViewController("/questions").setViewName("/questions/index");
        registry.addViewController("/answers").setViewName("/answers/index");
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("Messages");
        return messageSource;
    }

    /*@Bean
    public ClassLoaderTemplateResolver templateResolver(){
        var templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCheckExistence(true);
        return templateResolver;
    }*/

}
