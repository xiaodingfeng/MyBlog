package com.xiaobai.blog.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

/**
 * @author Xiaobai
 */
@Configuration
public class MvcConfigWeb implements WebMvcConfigurer {
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")) {
            registry.addResourceHandler(staticAccessPath).addResourceLocations("file:/" + uploadFolder);
        } else {
            registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadFolder);
        }

    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(uploadFolder);
        factory.setMaxFileSize(DataSize.parse("10MB"));
        factory.setMaxRequestSize(DataSize.parse("100MB"));
        return factory.createMultipartConfig();
    }

    /**
      * 解决异常信息：
      *  java.lang.IllegalArgumentException:
      *      Invalid character found in the request target. The valid characters are defined in RFC 7230 and RFC 3986
      * @return
      */
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
     TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
     factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
     @Override
     public void customize(Connector connector) {
     connector.setProperty("relaxedQueryChars", "|{}[]");
     }
     });
     return factory;
    }

}
