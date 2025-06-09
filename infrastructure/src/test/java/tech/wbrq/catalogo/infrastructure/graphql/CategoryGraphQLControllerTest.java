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
import tech.wbrq.catalogo.application.category.save.SaveCategoryUseCase;
import tech.wbrq.catalogo.domain.Fixture;
import tech.wbrq.catalogo.domain.category.Category;
import tech.wbrq.catalogo.domain.category.CategorySearchQuery;
import tech.wbrq.catalogo.domain.pagination.Pagination;
import tech.wbrq.catalogo.domain.utils.IdUtils;
import tech.wbrq.catalogo.domain.utils.InstantUtils;

import java.util.List;
import java.util.Map;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@GraphQLControllerTest(controllers = CategoryGraphQLController.class)
public class CategoryGraphQLControllerTest {

    @MockitoBean
    private ListCategoryUseCase listCategoryUseCase;

    @MockitoBean
    private SaveCategoryUseCase saveCategoryUseCase;

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
                query AlLCategories($search: String, $page: Int, $perPage: Int, $sort: String, $direction: String) {
                    categories(search: $search, page: $page, perPage: $perPage, sort: $sort, direction: $direction) {
                        id,
                        name
                    }
                }
                """;

        // When
        final var response = this.graphql.document(query)
                .variable("search", expectedSearch)
                .variable("page", expectedPage)
                .variable("perPage", expectedPerPage)
                .variable("sort", expectedSort)
                .variable("direction", expectedDirection)
                .execute();

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
    void givenACategoryInput_whenCallsSaveCategoryMutation_shouldPersistAndReturn() {
        // Given
        final var expectedId = IdUtils.uuid();
        final var expectedName = "Aulas";
        final var expectedDescription = "Aulas de programação";
        final var expectedIsActive = false;
        final var expectedCreatedAt = InstantUtils.now();
        final var expectedUpdatedAt = InstantUtils.now();
        final var expectedDeletedAt = InstantUtils.now();

        final var input = Map.of(
                "id", expectedId,
                "name", expectedName,
                "description", expectedDescription,
                "active", expectedIsActive,
                "createdAt", expectedCreatedAt.toString(),
                "updatedAt", expectedUpdatedAt.toString(),
                "deletedAt", expectedDeletedAt.toString()
        );

        final var query = """
                mutation SaveCategory($input: CategoryInput!) {
                    category: saveCategory(input: $input) {
                        id,
                        name,
                        description
                    }
                }
                """;

        doAnswer(returnsFirstArg()).when(saveCategoryUseCase).execute(any());

        // When
        this.graphql.document(query)
                .variable("input", input)
                .execute()
                .path("category.id").entity(String.class).isEqualTo(expectedId)
                .path("category.name").entity(String.class).isEqualTo(expectedName)
                .path("category.description").entity(String.class).isEqualTo(expectedDescription);

        // Then
        final var capturer = ArgumentCaptor.forClass(Category.class);

        verify(this.saveCategoryUseCase, times(1)).execute(capturer.capture());

        final var actualCategory = capturer.getValue();

        Assertions.assertEquals(expectedId, actualCategory.id());
        Assertions.assertEquals(expectedName, actualCategory.name());
        Assertions.assertEquals(expectedDescription, actualCategory.description());
        Assertions.assertEquals(expectedIsActive, actualCategory.active());
        Assertions.assertEquals(expectedCreatedAt, actualCategory.createdAt());
        Assertions.assertEquals(expectedUpdatedAt, actualCategory.updatedAt());
        Assertions.assertEquals(expectedDeletedAt, actualCategory.deletedAt());
    }
}
