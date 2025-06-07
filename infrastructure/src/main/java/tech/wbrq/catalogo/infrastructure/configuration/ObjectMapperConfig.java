package tech.wbrq.catalogo.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import tech.wbrq.catalogo.infrastructure.configuration.json.Json;

@JsonComponent
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return Json.mapper();
    }
}
