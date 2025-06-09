package tech.wbrq.catalogo.application.category.list;

import tech.wbrq.catalogo.domain.category.Category;

public record ListCategoryOutput(
        String id,
        String name,
        String description
) {
    public static ListCategoryOutput from(final Category category) {
        return new ListCategoryOutput(category.id(), category.name(), category.description());
    }
}
