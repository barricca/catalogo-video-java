package tech.wbrq.catalogo.domain.category;

import tech.wbrq.catalogo.domain.validation.Error;
import tech.wbrq.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category {
    private final String id;
    private final String name;
    private final String description;
    private final boolean active;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final Instant deletedAt;

    private Category(
            final String id,
            final String name,
            final String description,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

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

    public void validate(final ValidationHandler aHandler) {
        if (id == null || id.isBlank()) {
            aHandler.append(new Error("'id' should not be empty"));
        }
        if (name == null || name.isBlank()) {
            aHandler.append(new Error("'name' should not be empty"));
        }
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public boolean active() {
        return active;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public Instant deletedAt() {
        return deletedAt;
    }
}
