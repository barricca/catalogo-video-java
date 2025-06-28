package tech.wbrq.catalogo.infrastructure.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.wbrq.catalogo.AbstractElasticsearchTest;

public class CategoryElasticsearchGatewayTest extends AbstractElasticsearchTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testInjection() {
        Assertions.assertNotNull(categoryRepository);
    }
}
