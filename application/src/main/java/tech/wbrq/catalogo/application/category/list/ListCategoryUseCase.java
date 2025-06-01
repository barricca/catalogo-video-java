package tech.wbrq.catalogo.application.category.list;

import tech.wbrq.catalogo.application.UseCase;
import tech.wbrq.catalogo.domain.category.CategoryGateway;
import tech.wbrq.catalogo.domain.category.CategorySearchQuery;
import tech.wbrq.catalogo.domain.pagination.Pagination;

import java.util.Objects;

public class ListCategoryUseCase extends UseCase<CategorySearchQuery, Pagination<ListCategoryOutput>> {

    private final CategoryGateway categoryGateway;

    public ListCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<ListCategoryOutput> execute(final CategorySearchQuery aQuery) {
        return this.categoryGateway.findAll(aQuery).map(ListCategoryOutput::from);
    }
}
