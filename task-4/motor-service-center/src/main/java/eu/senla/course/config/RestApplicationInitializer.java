package eu.senla.course.config;

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
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(RestApplicationConfig.class, HibernateConfig.class, SecurityConfig.class);
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
