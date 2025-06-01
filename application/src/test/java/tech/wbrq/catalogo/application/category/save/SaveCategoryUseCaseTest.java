package tech.wbrq.catalogo.application.category.save;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tech.wbrq.catalogo.application.UseCaseTest;
import tech.wbrq.catalogo.domain.Fixture;
import tech.wbrq.catalogo.domain.category.Category;
import tech.wbrq.catalogo.domain.category.CategoryGateway;
import tech.wbrq.catalogo.domain.exceptions.DomainException;
import tech.wbrq.catalogo.domain.utils.IdUtils;
import tech.wbrq.catalogo.domain.utils.InstantUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UseCaseTest
public class SaveCategoryUseCaseTest {

    @InjectMocks
    private SaveCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    void givenValidCategory_whenCallsSave_shouldPersistIt() {
        // Given
        final var aCategory = Fixture.Categories.aulas();

        when(this.categoryGateway.save(any())).thenAnswer(returnsFirstArg());

        // When
        this.useCase.execute(aCategory);

        // Then
        verify(categoryGateway, times(1)).save(eq(aCategory));
    }

    @Test
    void givenInvalidNullCategory_whenCallsSave_shouldReturnError() {
        // Given
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'aCategory' cannot be null";

        // When
        final var actualError = assertThrows(DomainException.class, () -> this.useCase.execute(null));

        // Then
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getErrors().getFirst().message());

        verify(categoryGateway, times(0)).save(any());
    }

    @Test
    void givenInvalidName_whenCallsSave_shouldReturnError() {
        // Given
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var aCategory = Category.with(
                IdUtils.uuid(),
                " ",
                "Aulas de programação e tecnologia - gravadas",
                true,
                InstantUtils.now(),
                InstantUtils.now(),
                null
        );

        // When
        final var actualError = assertThrows(DomainException.class, () -> this.useCase.execute(aCategory));

        // Then
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getErrors().getFirst().message());

        verify(categoryGateway, times(0)).save(aCategory);
    }

    @Test
    void givenInvalidId_whenCallsSave_shouldReturnError() {
        // Given
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'id' should not be empty";

        final var aCategory = Category.with(
                " ",
                "Aulas",
                "Aulas de programação e tecnologia - gravadas",
                true,
                InstantUtils.now(),
                InstantUtils.now(),
                null
        );

        // When
        final var actualError = assertThrows(DomainException.class, () -> this.useCase.execute(aCategory));

        // Then
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getErrors().getFirst().message());

        verify(categoryGateway, times(0)).save(aCategory);
    }
}
