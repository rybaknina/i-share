package org.senla.share.rest;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.senla.share.filter.HttpRequestResponseLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.format.DateTimeFormatter;

@Configuration
@EnableWebMvc
@ComponentScan("org.senla.share")
@PropertySource({"classpath:hibernate.properties", "classpath:application.properties"})
public class RestApplicationConfiguration {
    private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public HttpRequestResponseLoggingFilter filter() {
        return new HttpRequestResponseLoggingFilter();
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public Jackson2ObjectMapperBuilder jsonCustomizer() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.simpleDateFormat(dateTimeFormat);
        builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
        builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));

        return builder;
    }
}
