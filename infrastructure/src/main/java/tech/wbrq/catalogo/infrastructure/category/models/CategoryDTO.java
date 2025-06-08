package tech.wbrq.catalogo.infrastructure.category.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import tech.wbrq.catalogo.domain.category.Category;

import java.time.Instant;

public record CategoryDTO(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("is_active") Boolean active,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt,
        @JsonProperty("deleted_at") Instant deletedAt
) {
    @Override
    public Boolean active() {
        return active != null ? active : Boolean.TRUE;
    }

    public Category toCategory() {
        return Category.with(id(), name(), description(), active(), createdAt(), updatedAt(), deletedAt());
    }
}
