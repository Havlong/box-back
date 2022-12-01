package magneticcode.boxback.controllers;

import magneticcode.boxback.dto.StatusDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @Value("${project.name:unknown}")
    private String projectName;
    @Value("${project.developer.name:unspecified}")
    private String projectDev;
    @Value("${project.version:v0.0}")
    private String version;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatusDTO handleIndex() {
        return new StatusDTO(true, version, projectName, projectDev);
    }
}
