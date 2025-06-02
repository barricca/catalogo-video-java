package tech.wbrq.catalogo.application.category.list;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tech.wbrq.catalogo.application.UseCaseTest;
import tech.wbrq.catalogo.domain.Fixture;
import tech.wbrq.catalogo.domain.category.CategoryGateway;
import tech.wbrq.catalogo.domain.category.CategorySearchQuery;
import tech.wbrq.catalogo.domain.pagination.Pagination;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@UseCaseTest
public class ListCategoryUseCaseTest {

    @InjectMocks
    private ListCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    void givenValidQuery_whenCallsListCategories_shouldReturnCategories() {
        // Given
        final var categories = List.of(
                Fixture.Categories.aulas(),
                Fixture.Categories.lives()
        );

        final var expectedItems = categories.stream()
                .map(ListCategoryOutput::from)
                .toList();

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "Algo";
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedItemsCount = 2;

        final var aQuery =
                new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var pagination = new Pagination<>(expectedPage, expectedPerPage, expectedItemsCount, categories);

        when(this.categoryGateway.findAll(any())).thenReturn(pagination);

        // When
        final var actualOutput = this.useCase.execute(aQuery);

        // Then
        Assertions.assertEquals(expectedPage, actualOutput.meta().currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.meta().perPage());
        Assertions.assertEquals(expectedItemsCount, actualOutput.meta().total());
        Assertions.assertTrue(
                expectedItems.size() == actualOutput.data().size()
                        && actualOutput.data().containsAll(expectedItems)
        );
    }
}
