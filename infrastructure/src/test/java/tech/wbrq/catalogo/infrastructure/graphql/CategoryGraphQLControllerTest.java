package tech.wbrq.catalogo.infrastructure.graphql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import tech.wbrq.catalogo.GraphQLControllerTest;
import tech.wbrq.catalogo.application.category.list.ListCategoryOutput;
import tech.wbrq.catalogo.application.category.list.ListCategoryUseCase;
import tech.wbrq.catalogo.domain.Fixture;
import tech.wbrq.catalogo.domain.pagination.Pagination;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@GraphQLControllerTest
public class CategoryGraphQLControllerTest {

    @MockitoBean
    private ListCategoryUseCase listCategoryUseCase;

    @Autowired
    private GraphQlTester graphql;

    @Test
    public void testListCategories() {
        // Given
        final var expectedCategories = List.of(
                ListCategoryOutput.from(Fixture.Categories.aulas()),
                ListCategoryOutput.from(Fixture.Categories.lives())
        );

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedSort = "name";
        final var expectedDirection = "asc";


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
    }
}
