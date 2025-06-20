package tech.wbrq.catalogo;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import tech.wbrq.catalogo.infrastructure.category.CategoryRepository;

public class IntegrationTestConfiguration {

    @Bean
    public CategoryRepository categoryRepository() {
        return Mockito.mock(CategoryRepository.class);
    }
}
