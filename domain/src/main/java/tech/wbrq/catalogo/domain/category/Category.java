package tech.wbrq.catalogo.domain.category;

import tech.wbrq.catalogo.domain.validation.Error;
import tech.wbrq.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;

public record Category(
        String id,
        String name,
        String description,
        boolean active,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {

    public static Category with(
            final String anId,
            final String name,
            final String description,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Category(
                anId,
                name,
                description,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public static Category with(final Category aCategory) {
        return with(
                aCategory.id(),
                aCategory.name,
                aCategory.description,
                aCategory.active(),
                aCategory.createdAt,
                aCategory.updatedAt,
                aCategory.deletedAt
        );
    }

    public Category validate(final ValidationHandler aHandler) {
        if (id == null || id.isBlank()) {
            aHandler.append(new Error("'id' should not be empty"));
        }
        if (name == null || name.isBlank()) {
            aHandler.append(new Error("'name' should not be empty"));
        }
        return this;
    }
}
