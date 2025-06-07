package tech.wbrq.catalogo.infrastructure.graphql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import tech.wbrq.catalogo.GraphQLControllerTest;
import tech.wbrq.catalogo.application.category.list.ListCategoryOutput;
import tech.wbrq.catalogo.application.category.list.ListCategoryUseCase;
import tech.wbrq.catalogo.domain.Fixture;
import tech.wbrq.catalogo.domain.category.CategorySearchQuery;
import tech.wbrq.catalogo.domain.pagination.Pagination;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@GraphQLControllerTest
public class CategoryGraphQLControllerTest {

    @MockitoBean
    private ListCategoryUseCase listCategoryUseCase;

    @Autowired
    private GraphQlTester graphql;

    @Test
    public void givenDefaultArguments_whenCallsListCategories_shouldReturn() {
        // Given
        final var expectedCategories = List.of(
                ListCategoryOutput.from(Fixture.Categories.aulas()),
                ListCategoryOutput.from(Fixture.Categories.lives())
        );

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedSearch = "";

        when(this.listCategoryUseCase.execute(any())).thenReturn(
                new Pagination<>(expectedPage, expectedPerPage, expectedCategories.size(), expectedCategories)
        );

        final var query = """
                {
                    categories {
                        id,
                        name
                    }
                }
                """;

        // When
        final var response = this.graphql.document(query).execute();

        final var actualCategories = response.path("categories")
                .entityList(ListCategoryOutput.class)
                .get();

        // Then
        Assertions.assertTrue(actualCategories.size() == expectedCategories.size()
                && actualCategories.containsAll(expectedCategories)
        );

        final var capturer = ArgumentCaptor.forClass(CategorySearchQuery.class);

        verify(this.listCategoryUseCase).execute(capturer.capture());

        final var actualQuery = capturer.getValue();

        Assertions.assertEquals(expectedPage, actualQuery.page());
        Assertions.assertEquals(expectedPerPage, actualQuery.perPage());
        Assertions.assertEquals(expectedSort, actualQuery.sort());
        Assertions.assertEquals(expectedDirection, actualQuery.direction());
        Assertions.assertEquals(expectedSearch, actualQuery.terms());
    }

    @Test
    public void givenCustomArguments_whenCallsListCategories_shouldReturn() {
        // Given
        final var expectedCategories = List.of(
                ListCategoryOutput.from(Fixture.Categories.aulas()),
                ListCategoryOutput.from(Fixture.Categories.lives())
        );

        final var expectedPage = 2;
        final var expectedPerPage = 15;
        final var expectedSort = "id";
        final var expectedDirection = "desc";
        final var expectedSearch = "uiu";

        when(this.listCategoryUseCase.execute(any())).thenReturn(
                new Pagination<>(expectedPage, expectedPerPage, expectedCategories.size(), expectedCategories)
        );

        final var query = """
                {
                    categories(search: "%s", page: %d, perPage: %d, sort: "%s", direction: "%s") {
                        id,
                        name
                    }
                }
                """.formatted(expectedSearch, expectedPage, expectedPerPage, expectedSort, expectedDirection);

        // When
        final var response = this.graphql.document(query).execute();

        final var actualCategories = response.path("categories")
                .entityList(ListCategoryOutput.class)
                .get();

        // Then
        Assertions.assertTrue(actualCategories.size() == expectedCategories.size()
                && actualCategories.containsAll(expectedCategories)
        );

        final var capturer = ArgumentCaptor.forClass(CategorySearchQuery.class);

        verify(this.listCategoryUseCase).execute(capturer.capture());

        final var actualQuery = capturer.getValue();

        Assertions.assertEquals(expectedPage, actualQuery.page());
        Assertions.assertEquals(expectedPerPage, actualQuery.perPage());
        Assertions.assertEquals(expectedSort, actualQuery.sort());
        Assertions.assertEquals(expectedDirection, actualQuery.direction());
        Assertions.assertEquals(expectedSearch, actualQuery.terms());
    }
}
