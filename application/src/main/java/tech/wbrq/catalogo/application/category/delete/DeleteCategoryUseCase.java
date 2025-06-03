package tech.wbrq.catalogo.application.category.delete;

import tech.wbrq.catalogo.application.UnitUseCase;
import tech.wbrq.catalogo.domain.category.CategoryGateway;

import java.util.Objects;

public class DeleteCategoryUseCase extends UnitUseCase<String> {

    private final CategoryGateway categoryGateway;

    public DeleteCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public void execute(final String anId) {
        if (anId == null) {
            return;
        }
        this.categoryGateway.deleteById(anId);
    }
}
