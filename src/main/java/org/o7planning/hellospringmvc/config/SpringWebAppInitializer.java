package org.o7planning.hellospringmvc.config;

/*
 * 初始化  注册servlet

SpringWebAppInitializer is class that implements WebApplicationInitializer interface. 

Spring will read the configuration information in this class to initial your Web application. 

In this class, you can normally register Servlets, Servlet Filters, and Servlet Listeners instead of registering them in web.xml. 

An important servlet is org.springframework.web.servlet.DispatcherServlet, you can register it.

*/

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
 
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
 
public class SpringWebAppInitializer implements WebApplicationInitializer {
 
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(ApplicationContextConfig.class);
 
        // Dispatcher Servlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("SpringDispatcher",
                new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
         
        dispatcher.setInitParameter("contextClass", appContext.getClass().getName());
 
        servletContext.addListener(new ContextLoaderListener(appContext));
         
        // UTF8 Charactor Filter.
        FilterRegistration.Dynamic fr = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
 
        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/*");        
    }
 
}