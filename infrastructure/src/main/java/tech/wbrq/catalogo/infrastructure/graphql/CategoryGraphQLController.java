package tech.wbrq.catalogo.infrastructure.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import tech.wbrq.catalogo.application.category.list.ListCategoryOutput;
import tech.wbrq.catalogo.application.category.list.ListCategoryUseCase;
import tech.wbrq.catalogo.application.category.save.SaveCategoryUseCase;
import tech.wbrq.catalogo.domain.category.Category;
import tech.wbrq.catalogo.domain.category.CategorySearchQuery;
import tech.wbrq.catalogo.infrastructure.category.models.CategoryDTO;

import java.util.List;
import java.util.Objects;

@Controller
public class CategoryGraphQLController {

    private final ListCategoryUseCase listCategoryUseCase;
    private final SaveCategoryUseCase saveCategoryUseCase;

    public CategoryGraphQLController(
            final ListCategoryUseCase listCategoryUseCase,
            final SaveCategoryUseCase saveCategoryUseCase
    ) {
        this.listCategoryUseCase = Objects.requireNonNull(listCategoryUseCase);
        this.saveCategoryUseCase = Objects.requireNonNull(saveCategoryUseCase);
    }

    @QueryMapping
    public List<ListCategoryOutput> categories(
            @Argument final String search,
            @Argument final int page,
            @Argument final int perPage,
            @Argument final String sort,
            @Argument final String direction
    ) {
        final var aQuery = new CategorySearchQuery(page, perPage, search, sort, direction);

        return this.listCategoryUseCase.execute(aQuery).data();
    }

    @MutationMapping
    public Category saveCategory(@Argument final CategoryDTO input) {
        return this.saveCategoryUseCase.execute(input.toCategory());
    }
}
