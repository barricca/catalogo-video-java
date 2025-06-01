package tech.wbrq.catalogo.domain.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.wbrq.catalogo.domain.Fixture;
import tech.wbrq.catalogo.domain.UnitTest;
import tech.wbrq.catalogo.domain.exceptions.DomainException;
import tech.wbrq.catalogo.domain.utils.IdUtils;
import tech.wbrq.catalogo.domain.utils.InstantUtils;
import tech.wbrq.catalogo.domain.validation.handler.ThrowsValidationHandler;

@UnitTest
class CategoryTest {

    @Test
    void givenAValidParams_whenCallWith_thenInstantiateACategory() {
        // Given
        final var expectedId = IdUtils.uuid();
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedDates = InstantUtils.now();

        // When
        final var actualCategory = Category.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive,
                expectedDates,
                expectedDates,
                null
        );

        // Then
        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(expectedId, actualCategory.id());
        Assertions.assertEquals(expectedName, actualCategory.name());
        Assertions.assertEquals(expectedDescription, actualCategory.description());
        Assertions.assertEquals(expectedIsActive, actualCategory.active());
        Assertions.assertEquals(expectedDates, actualCategory.createdAt());
        Assertions.assertEquals(expectedDates, actualCategory.updatedAt());
        Assertions.assertNull(actualCategory.deletedAt());
    }

    @Test
    void givenAValidParams_whenCallWithCategory_thenInstantiateACategory() {
        // Given
        final var aCategory = Fixture.Categories.aulas();

        // When
        final var actualCategory = Category.with(aCategory);

        // Then
        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(aCategory.id(), actualCategory.id());
        Assertions.assertEquals(aCategory.name(), actualCategory.name());
        Assertions.assertEquals(aCategory.description(), actualCategory.description());
        Assertions.assertEquals(aCategory.active(), actualCategory.active());
        Assertions.assertEquals(aCategory.createdAt(), actualCategory.createdAt());
        Assertions.assertEquals(aCategory.updatedAt(), actualCategory.updatedAt());
        Assertions.assertEquals(aCategory.deletedAt(), actualCategory.deletedAt());
    }

    @Test
    void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        // Given
        final String expectedId = IdUtils.uuid();
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedDates = InstantUtils.now();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualCategory = Category.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive,
                expectedDates,
                expectedDates,
                null
        );

        // When
        final var actualException = Assertions.assertThrows(
                DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler())
        );

        // Then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
    }

    @Test
    void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        // Given
        final var expectedId = IdUtils.uuid();
        final var expectedName = " ";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedDates = InstantUtils.now();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualCategory = Category.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive,
                expectedDates,
                expectedDates,
                null
        );

        // When
        final var actualException = Assertions.assertThrows(
                DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler())
        );

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
    }

    @Test
    void givenAnInvalidNullId_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        // Given
        final String expectedId = null;
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedDates = InstantUtils.now();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'id' should not be empty";

        final var actualCategory = Category.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive,
                expectedDates,
                expectedDates,
                null
        );

        // When
        final var actualException = Assertions.assertThrows(
                DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler())
        );

        // Then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
    }

    @Test
    void givenAnInvalidEmptyId_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        // Given
        final var expectedId = "";
        final var expectedName = " ";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedDates = InstantUtils.now();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'id' should not be empty";

        final var actualCategory = Category.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive,
                expectedDates,
                expectedDates,
                null
        );

        // When
        final var actualException = Assertions.assertThrows(
                DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler())
        );

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
    }
}
