package org.windwant.jetty.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {
        "org.windwant.jetty"})
@PropertySource(value = "classpath:config.properties", ignoreResourceNotFound = true)
@EnableWebMvc
public class WebMvcConfig implements EnvironmentAware, WebMvcConfigurer {

    private Environment environment;

    @Autowired
    private LocalValidatorFactoryBean localValidatorFactoryBean;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new FastJsonHttpMessageConverter());
        // add stream support
        converters.add(new ByteArrayHttpMessageConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    @Override
    public Validator getValidator() {
        return localValidatorFactoryBean;
    }

    @Bean
    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/lang/messages");
        messageSource.setCacheSeconds(20);
        messageSource.setDefaultEncoding("utf-8");
        return messageSource;
    }

    /**
     * 方法级别验证
     * @param localValidatorFactoryBean
     * @return
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(LocalValidatorFactoryBean localValidatorFactoryBean) {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidator(localValidatorFactoryBean);
        return postProcessor;
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource) {
        LocalValidatorFactoryBean validatorFactory = new LocalValidatorFactoryBean();
        validatorFactory.setProviderClass(HibernateValidator.class);
        validatorFactory.setValidationMessageSource(reloadableResourceBundleMessageSource);
        return validatorFactory;
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
