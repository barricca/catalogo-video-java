package tech.wbrq.catalogo.infrastructure.configuration.usecases;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.wbrq.catalogo.application.category.delete.DeleteCategoryUseCase;
import tech.wbrq.catalogo.application.category.list.ListCategoryUseCase;
import tech.wbrq.catalogo.application.category.save.SaveCategoryUseCase;
import tech.wbrq.catalogo.domain.category.CategoryGateway;

import java.util.Objects;

@Configuration
public class CategoryUseCasesConfig {

    private final CategoryGateway categoryGateway;

    public CategoryUseCasesConfig(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DeleteCategoryUseCase(this.categoryGateway);
    }

    @Bean
    public ListCategoryUseCase listCategoryUseCase() {
        return new ListCategoryUseCase(this.categoryGateway);
    }

    @Bean
    public SaveCategoryUseCase saveCategoryUseCase() {
        return new SaveCategoryUseCase(this.categoryGateway);
    }
}
