package org.senla.share.rest;

import org.senla.share.hibernate.HibernateConfiguration;
import org.senla.share.jwt.JwtConfiguration;
import org.senla.share.logger.Log4j2ConfigurationFactory;
import org.senla.share.message.MessageSourceConfiguration;
import org.senla.share.security.SecurityConfiguration;
import org.senla.share.swagger.SwaggerConfiguration;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class RestApplicationInitializer implements WebApplicationInitializer {
    private static final String MAPPING_URL = "/*";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        System.setProperty("log4j.configurationFactory", Log4j2ConfigurationFactory.class.getName());
        Configurator.setRootLevel(Level.ERROR);

        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(RestApplicationConfiguration.class, JwtConfiguration.class, MessageSourceConfiguration.class,
                HibernateConfiguration.class, SecurityConfiguration.class, SwaggerConfiguration.class,
                org.springdoc.core.SwaggerUiConfigProperties.class, org.springdoc.core.SwaggerUiOAuthProperties.class,
                org.springdoc.core.SpringDocConfiguration.class, org.springdoc.core.SpringDocConfigProperties.class,
                org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(ctx));
        ctx.setServletContext(servletContext);
        final DispatcherServlet dispatcherServlet = new DispatcherServlet(ctx);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", dispatcherServlet);
        registration.addMapping(MAPPING_URL);
        registration.setLoadOnStartup(1);
        final DelegatingFilterProxy springSecurityFilterChain = new DelegatingFilterProxy("springSecurityFilterChain");
        final FilterRegistration.Dynamic addedFilter = servletContext.addFilter("springSecurityFilterChain", springSecurityFilterChain);
        addedFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
