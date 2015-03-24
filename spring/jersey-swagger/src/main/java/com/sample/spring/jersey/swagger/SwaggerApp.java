package com.sample.spring.jersey.swagger;

import com.sample.spring.jersey.swagger.exception.AllExceptionMapper;
import com.sample.spring.jersey.swagger.rest.EmployeeController;
import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.ReflectiveJaxrsScanner;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResource;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.message.MessageProperties;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("api")
public class SwaggerApp extends ResourceConfig {

    public SwaggerApp(){
        register(EmployeeController.class);
        register(JacksonFeature.class);
        register(AllExceptionMapper.class);

        register(ApiListingResource.class);
        register(ApiDeclarationProvider.class);
        register(ApiListingResourceJSON.class);
        register(ResourceListingProvider.class);

        property(MessageProperties.XML_FORMAT_OUTPUT, true);
        property(ServerProperties.TRACING, "ALL");
    }

    @PostConstruct
    /**
     * Initializes Swagger Configuration
     */
    public void initializeSwaggerConfiguration() {

        final ReflectiveJaxrsScanner scanner = new ReflectiveJaxrsScanner();
        scanner.setResourcePackage("com.sample.spring.jersey.swagger");
        ScannerFactory.setScanner(scanner);
        ClassReaders.setReader(new DefaultJaxrsApiReader());
        final SwaggerConfig config = ConfigFactory.config();
        config.setApiVersion("1.0");
        config.setBasePath("http://localhost:8080/jersey-swagger/rest");
    }
}
