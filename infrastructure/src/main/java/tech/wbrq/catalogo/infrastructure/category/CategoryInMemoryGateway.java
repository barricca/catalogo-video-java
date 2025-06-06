package tech.wbrq.catalogo.infrastructure.category;

import org.springframework.stereotype.Component;
import tech.wbrq.catalogo.domain.category.Category;
import tech.wbrq.catalogo.domain.category.CategoryGateway;
import tech.wbrq.catalogo.domain.category.CategorySearchQuery;
import tech.wbrq.catalogo.domain.pagination.Pagination;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CategoryInMemoryGateway implements CategoryGateway {

    private final ConcurrentHashMap<String, Category> db;

    public CategoryInMemoryGateway() {
        this.db = new ConcurrentHashMap<>();
    }

    @Override
    public Category save(final Category aCategory) {
        this.db.put(aCategory.id(), aCategory);
        return aCategory;
    }

    @Override
    public void deleteById(final String anId) {
        this.db.remove(anId);
    }

    @Override
    public Optional<Category> findById(final String anId) {
        return Optional.ofNullable(this.db.get(anId));
    }

    @Override
    public Pagination<Category> findAll(final CategorySearchQuery aQuery) {
        final var values = this.db.values();
        return new Pagination<>(aQuery.page(), aQuery.perPage(), values.size(), new ArrayList<>(values));
    }
}
