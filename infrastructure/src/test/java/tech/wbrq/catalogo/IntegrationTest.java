package tech.wbrq.catalogo;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tech.wbrq.catalogo.infrastructure.configuration.WebServerConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test-integration")
@EnableAutoConfiguration(exclude = {
        ElasticsearchRepositoriesAutoConfiguration.class
})
@SpringBootTest(classes = {WebServerConfig.class, IntegrationTestConfiguration.class})
@Tag("integrationTest")
public @interface IntegrationTest {
}
