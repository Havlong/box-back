package magneticcode.boxback;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SpringFoxConfig {
    @Value("${project.name:unknown}")
    private String projectName;
    @Value("${project.description:unspecified}")
    private String projectDescription;
    @Value("${project.developer.name:unspecified}")
    private String developer;
    @Value("${project.developer.email:unspecified}")
    private String devEmail;
    @Value("${project.developer.url:unspecified}")
    private String devUrl;
    @Value("${project.version:v0.0}")
    private String version;

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(projectName,
                projectDescription,
                version,
                "",
                new Contact(developer, devUrl, devEmail),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList());
    }
}
