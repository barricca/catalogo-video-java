package tech.wbrq.catalogo.application.category.delete;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tech.wbrq.catalogo.application.UseCaseTest;
import tech.wbrq.catalogo.domain.Fixture;
import tech.wbrq.catalogo.domain.category.CategoryGateway;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@UseCaseTest
public class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    void givenAValidId_whenCallsDeleteCategory_shouldBeOk() {
        // Given
        final var aulas = Fixture.Categories.aulas();
        final var expectedId = aulas.id();

        doNothing()
            .when(this.categoryGateway).deleteById(expectedId);

        // When
        Assertions.assertDoesNotThrow(() -> this.useCase.execute(expectedId));

        // Then
        verify(this.categoryGateway, times(1)).deleteById(expectedId);
    }
}
