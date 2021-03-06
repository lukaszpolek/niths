package no.niths.common.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;

/**
 * Main configuration class for the application
 *
 */
@Configuration
@ImportResource( { "classpath*:timerTaskConfig.xml" } )
public class AppConfig {

    public static final String BASE_PACKAGE =
                                   "no.niths",
                               EXTERNAL_PACKAGE =
                                   BASE_PACKAGE + ".external",
                               REST_PACKAGE =
                                   BASE_PACKAGE + ".application.rest",
                               SERVICES_PACKAGE =
                                   BASE_PACKAGE + ".services",
                               WEB_PACKAGE =
                                   BASE_PACKAGE + ".application.web",
                                   
                               APP_PROPS = "application.properties";

    @Bean
    public static PropertyPlaceholderConfigurer properties(){
        final PropertyPlaceholderConfigurer ppc =
                new PropertyPlaceholderConfigurer();
        final ClassPathResource[] resources = new ClassPathResource[] {
                new ClassPathResource(APP_PROPS)};
        ppc.setLocations(resources);
        ppc.setIgnoreUnresolvablePlaceholders(true);

        return ppc;
    }
}
